/***********************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.chart.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.birt.chart.ui.plugin.ChartUIPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class has been created to hold methods that provide specific functionality or services.
 * 
 * @author Actuate Corporation
 */
public final class UIHelper
{

    /**
     * This is a helper method created to get the location on screen of a composite. It does not take into account
     * multiple monitors.
     * 
     * @param cmpTarget
     *            The composite whose location on screen is required
     * @return The location of the composite on screen.
     */
    public static Point getScreenLocation(Composite cmpTarget)
    {
        Point ptScreen = new Point(0, 0);
        try
        {
            Composite cTmp = cmpTarget;
            while (!(cTmp instanceof Shell))
            {
                ptScreen.x += cTmp.getLocation().x;
                ptScreen.y += cTmp.getLocation().y;
                cTmp = cTmp.getParent();
            }
        }
        catch (Exception e )
        {
            e.printStackTrace();
        }
        return cmpTarget.getShell().toDisplay(ptScreen);
    }

    /**
     * This is a helper method created to center a shell on the screen. It centers the shell on the primary monitor in a
     * multi-monitor configuration.
     * 
     * @param shell
     *            The shell to be centered on screen
     */
    public static void centerOnScreen(Shell shell)
    {
        shell.setLocation(Display.getCurrent().getPrimaryMonitor().getClientArea().width / 2 - (shell.getSize().x / 2),
            Display.getCurrent().getPrimaryMonitor().getClientArea().height / 2 - (shell.getSize().y / 2));
    }

    /**
     * This method returns an URL for a resource given its plugin relative path. It is intended to be used to abstract
     * out the usage of the UI as a plugin or standalone component when it comes to accessing resources.
     * 
     * @param sPluginRelativePath
     *            The path to the resource relative to the plugin location.
     * @return URL representing the location of the resource.
     */
    public static URL getURL(String sPluginRelativePath)
    {
        URL url = null;
        if (Platform.getExtensionRegistry() != null)
        {
            try
            {
                url = new URL(ChartUIPlugin.getDefault().getBundle().getEntry("/"), sPluginRelativePath); //$NON-NLS-1$
            }
            catch (MalformedURLException e )
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                url = new URL("file:///" + new File(sPluginRelativePath).getAbsolutePath()); //$NON-NLS-1$
            }
            catch (MalformedURLException e )
            {
                e.printStackTrace();
            }
        }

        return url;
    }

    /**
     * This is a convenience method to get an imgIcon from a URL.
     * 
     * @param url
     *            The URL for the imgIcon.
     * @return The imgIcon represented by the given URL.
     */
    public static Image getImage(String sPluginRelativePath)
    {
        org.eclipse.swt.graphics.Image img = null;
        try
        {
            try
            {
                img = new org.eclipse.swt.graphics.Image(Display.getCurrent(), getURL(sPluginRelativePath).openStream());
            }
            catch (MalformedURLException e1 )
            {
                img = new org.eclipse.swt.graphics.Image(Display.getCurrent(), new FileInputStream(getURL(
                    sPluginRelativePath).toString()));
            }
        }
        catch (FileNotFoundException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return img;
    }
}