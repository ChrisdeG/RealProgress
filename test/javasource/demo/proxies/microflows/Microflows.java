// This file was generated by Mendix Business Modeler 5.0.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package demo.proxies.microflows;

import java.util.HashMap;
import java.util.Map;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class Microflows
{
	// These are the microflows for the Demo module

	public static demo.proxies.Customer getCustomer(IContext context)
	{
		try
		{
			Map<String, Object> params = new HashMap<String, Object>();
			IMendixObject result = (IMendixObject)Core.execute(context, "Demo.GetCustomer", params);
			return result == null ? null : demo.proxies.Customer.initialize(context, result);
		}
		catch (CoreException e)
		{
			throw new MendixRuntimeException(e);
		}
	}

	public static demo.proxies.Customer mC_GetProgress(IContext context, demo.proxies.Customer _customer)
	{
		try
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("Customer", _customer == null ? null : _customer.getMendixObject());
			IMendixObject result = (IMendixObject)Core.execute(context, "Demo.MC_GetProgress", params);
			return result == null ? null : demo.proxies.Customer.initialize(context, result);
		}
		catch (CoreException e)
		{
			throw new MendixRuntimeException(e);
		}
	}

	public static void mC_SampleLongAction(IContext context, demo.proxies.Customer _customer)
	{
		try
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("Customer", _customer == null ? null : _customer.getMendixObject());
			Core.execute(context, "Demo.MC_SampleLongAction", params);
		}
		catch (CoreException e)
		{
			throw new MendixRuntimeException(e);
		}
	}

	public static void openPopup(IContext context, demo.proxies.Customer _customer)
	{
		try
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("Customer", _customer == null ? null : _customer.getMendixObject());
			Core.execute(context, "Demo.OpenPopup", params);
		}
		catch (CoreException e)
		{
			throw new MendixRuntimeException(e);
		}
	}

	public static void refresh(IContext context, demo.proxies.Customer _customer)
	{
		try
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("Customer", _customer == null ? null : _customer.getMendixObject());
			Core.execute(context, "Demo.Refresh", params);
		}
		catch (CoreException e)
		{
			throw new MendixRuntimeException(e);
		}
	}

	public static boolean showProgress(IContext context, demo.proxies.Customer _customer)
	{
		try
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("Customer", _customer == null ? null : _customer.getMendixObject());
			return (Boolean)Core.execute(context, "Demo.ShowProgress", params);
		}
		catch (CoreException e)
		{
			throw new MendixRuntimeException(e);
		}
	}
}