/*global
    console, mx, logger, require, define, setInterval
 */
/*jslint
    this, 
*/

/*
RealProgress
========================

@file      : RealProgress.js
@version   : 1.0.0
@author    : Chris de Gelder
@date      : 2016-08-30
@copyright : Chris de Gelder 2016
@license   : Apache 2

Documentation
========================
Describe your widget here.
 */

// Required module list. Remove unnecessary modules, you can always get them back from the boilerplate.
define([
		"dojo/_base/declare",
		"mxui/widget/_WidgetBase",

		"mxui/dom",
		"dojo/dom",
		"dojo/dom-prop",
		"dojo/dom-geometry",
		"dojo/dom-class",
		"dojo/dom-style",
		"dojo/dom-construct",
		"dojo/_base/array",
		"dojo/_base/lang",
		"dojo/text",
		"dojo/html",
		"dojo/_base/event",
		"dijit/ProgressBar",

		"RealProgress/lib/jquery-1.11.2",
		"dojo/text!RealProgress/widget/template/RealProgress.html"
	], function (declare, _WidgetBase, dom, dojoDom, dojoProp, dojoGeometry, dojoClass, dojoStyle, dojoConstruct, dojoArray, dojoLang, dojoText, dojoHtml, dojoEvent, ProgressBar, _jQuery) {
	"use strict";

	var $ = _jQuery.noConflict(true);

	// Declare widget's prototype.
	return declare("RealProgress.widget.RealProgress", [_WidgetBase], {

		// DOM elements

		// Parameters configured in the Modeler.
		mfToExecute : "",


		// Internal variables. Non-primitives created in the prototype are shared between all widget instances.
		handles : null,
		contextObj : null,
		myProgressBar : null,
		_timeout: null,
		_timerStarted: false,
		startMFDone: false,

		// dojo.declare.constructor is called to construct the widget instance. Implement to initialize non-primitive properties.
		constructor : function () {
			logger.debug(this.id + ".constructor");
			this.handles = [];
		},

		postCreate : function () {
			logger.debug(this.id + ".postCreate");
			// create a progressbar and put in this node.
			this.myProgressBar = new ProgressBar({
					//style : "width: 300px"
				});
			dojoConstruct.place(this.myProgressBar.domNode, this.domNode);
		},
		// mendix wants this.
		resize : function () {
		},
		update : function (obj, callback) {
			console.log(this.id + ".update", obj); 

			if (obj != this.contextObj) {
				this.contextObj = obj;
				//this.resetSubscriptions();
				this.callStartMF(); 
				this._runTimer();
			}
			if (callback && typeof callback === "function") {
				console.log(this.id + "._renderString callback"); 
				callback();
			}
		},
		/*
			callMF is repetively calling a microflow this is supposed to wait a while (500-10000 ms) before answering.
		*/
		callMF : function () {
			console.log('_callmf - ', this.count, this);
			this.count += 1;
			if (this.contextObj && this.mfToExecute !== "") {
				mx.data.action({
					params : {
						applyto : "selection",
						actionname : this.mfToExecute,
						guids : [this.contextObj.getGuid()]
					},
					store : {
						caller : this.mxform
					},
					callback : dojoLang.hitch(this, function (objs) {
						var progress = objs[0].get(this.progressattr);
						this.myProgressBar.set("value", progress);
						var message = objs[0].get(this.messageattr);
						console.log('callback returned', progress , message); 
						if (message) {
							this.myProgressBar.set("label", message);
						}
						if (progress >= 100)  {
							this._stopTimer();
							if (this.closeWhenDone) {
								this.mxform.close();
							}
						}
					}),
					error : dojoLang.hitch(this, function (error) {
						logger.error(this.id + ": An error occurred while executing microflow: " + error.description);
					})
				}, this);
			}
		},
		callStartMF : function () {
			console.log('_call staring mf' + this.mfAtStart);
			if (this.startMFDone == false && this.contextObj && this.mfAtStart !== "") {
				this.startMFDone = true;
				mx.data.action({
					params : {
						applyto : "selection",
						actionname : this.mfAtStart,
						guids : [this.contextObj.getGuid()]
					},
					store : {
						caller : this.mxform
					},
					callback : dojoLang.hitch(this, function (objs) {
						console.log('callback returned', objs); 
					}),
					error : dojoLang.hitch(this, function (error) {
						logger.error(this.id + ": An error occurred while executing microflow: " + this.mfAtStart + " "  + error.description);
					})
				}, this);
			}
		},
		uninitialize: function () {
            this._stopTimer();
        },

        _runTimer: function () {
            logger.debug(this.id + "._runTimer", this.interval);
            if (this.mfToExecute !== "" && this.contextObj) {
                this._timerStarted = true;
                this._timer = setInterval(dojoLang.hitch(this, this.callMF), this.interval>=1000?this.interval:1000);
            }
        },

        _stopTimer: function () {
            logger.debug(this.id + "._stopTimer");
            if (this._timer !== null) {
                logger.debug(this.id + "._stopTimer timer cleared");
                clearInterval(this._timer);
                this._timer = null;
            }
            if (this._timeout !== null) {
                logger.debug(this.id + "._stopTimer timeout cleared");
                clearTimeout(this._timeout);
                this._timeout = null;
            }
        }


	});
});

require(["RealProgress/widget/RealProgress"]);
