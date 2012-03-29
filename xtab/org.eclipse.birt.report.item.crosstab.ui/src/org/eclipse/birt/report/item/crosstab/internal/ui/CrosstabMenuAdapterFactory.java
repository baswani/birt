/*******************************************************************************
 * Copyright (c) 2008 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.item.crosstab.internal.ui;

import org.eclipse.birt.report.designer.internal.ui.editors.schematic.providers.ISchematicMenuListener;
import org.eclipse.birt.report.item.crosstab.core.de.CrosstabReportItemHandle;
import org.eclipse.birt.report.item.crosstab.internal.ui.editors.action.AddComputedMeasureAction;
import org.eclipse.birt.report.item.crosstab.internal.ui.editors.action.AddRelativeTimePeriodAction;
import org.eclipse.birt.report.model.api.ExtendedItemHandle;
import org.eclipse.birt.report.model.api.extension.ExtendedElementException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;

/**
 * 
 */

public class CrosstabMenuAdapterFactory implements IAdapterFactory
{

	@Override
	public Object getAdapter( Object adaptableObject, Class adapterType )
	{
		if ( adaptableObject instanceof ExtendedItemHandle
				&& ( (ExtendedItemHandle) adaptableObject ).getExtensionName( )
						.equals( "Crosstab" )
				&& adapterType == IMenuListener.class )
		{
			final ExtendedItemHandle handle = (ExtendedItemHandle)adaptableObject;
			
			return new ISchematicMenuListener( ) {

				public void menuAboutToShow( IMenuManager manager )
				{
					CrosstabReportItemHandle crosstab = null;
					try
					{
						crosstab = (CrosstabReportItemHandle)( (ExtendedItemHandle) handle ).getReportItem( );
					}
					catch ( ExtendedElementException e )
					{
						return;
					}
					//manager.appendToGroup( "additions", new AddComputedMeasureAction(  ); //$NON-NLS-1$
					manager.appendToGroup( GEFActionConstants.GROUP_VIEW,
							new AddComputedMeasureAction( crosstab ) );
					manager.appendToGroup( GEFActionConstants.GROUP_VIEW,
							new AddRelativeTimePeriodAction( handle ) );
				}

				public void setActionRegistry( ActionRegistry actionRegistry )
				{

				}
			};
		}
		return null;
	}

	@Override
	public Class[] getAdapterList( )
	{
		return null;
	}

}
