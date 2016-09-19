### RealProgress Module and widget

### Description

Provides real time progress bar when the microflow is running.

### Typical usage scenario

Display progress for long running actions to inform the user and prevent unwanted button clicks

### Features and limitations

Only available for a user. Background tasks can not be displayed.

Displays progress and optionally a message.

### Dependencies

None

### Installation

Import his module.

### Configuration

#### Microflow

In the long running microflow add calls to the Java-action **setProgressMax** and **setProgress.** First determine the total number of objects that must be processed. Pass that to the Java-action **setProgressMax.** Keep a counter what your current position is. Call **setProgress** with the updated count.

#### User interface

Create an attribute on the context object that contains the progress (integer) and optionally a message attribute if you want to display text in the widget.

Put the RealProgress widget on a form with context. Set the properties:

1.  Progress Entity: same as context

2.  Progress attribute: As defined in step 1

3.  Message attribute (optional) as defined in step 1

4.  Progress\_microflow: create a new Microflow

**Progress microflow**

In the progress microflow set this:

1.  Call the Java action GetProgress with the context object as parameter

2.  Set the progress attribute with the result

3.  If you want to use a message: call the java action GetProgressMessage

4.  Set the progress message attribute with the result

5.  Return the context object

#### Popup progress form

For a popup set the after start microflow to your actual saving and progress microflow and set the property 'close form when done' to 'Yes'. From the save/progress button show this as a blocking popup and go.

### Limitations

Don't set the interval below 5000 milliseconds. It will cause overhead for too much polling.

### Frequently Asked Questions \[optional\]

-   **Q:**

-   **A:**
