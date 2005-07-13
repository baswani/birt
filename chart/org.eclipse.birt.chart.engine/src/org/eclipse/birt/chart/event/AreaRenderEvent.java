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

package org.eclipse.birt.chart.event;

import java.util.ArrayList;

import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.log.ILogger;
import org.eclipse.birt.chart.log.Logger;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.Fill;
import org.eclipse.birt.chart.model.attribute.LineAttributes;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * 
 */
public final class AreaRenderEvent extends PrimitiveRenderEvent
{

	private static final long serialVersionUID = 4924819106091024348L;

	private final ArrayList alLinesAndArcs = new ArrayList( );

	private Fill fill;

	private LineAttributes lia;

	private static ILogger logger = Logger.getLogger( "org.eclipse.birt.chart.engine/event" ); //$NON-NLS-1$

	/**
	 * @param oSource
	 */
	public AreaRenderEvent( Object oSource )
	{
		super( oSource );
	}

	/**
	 * 
	 */
	public final void clear( )
	{
		alLinesAndArcs.clear( );
	}

	/**
	 * 
	 * @param pre
	 */
	public final void add( PrimitiveRenderEvent pre )
	{
		alLinesAndArcs.add( pre );
	}

	/**
	 * 
	 * @return
	 */
	public final int getElementCount( )
	{
		return alLinesAndArcs.size( );
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public final PrimitiveRenderEvent getElement( int i )
	{
		return (PrimitiveRenderEvent) alLinesAndArcs.get( i );
	}

	/**
	 * @return Returns the fill.
	 */
	public final Fill getBackground( )
	{
		return fill;
	}

	/**
	 * @param fill
	 *            The fill to set.
	 */
	public final void setBackground( Fill fill )
	{
		this.fill = fill;
	}

	/**
	 * Returns the bounds of all combined elements in this 'area'
	 * 
	 * @return
	 */
	public final Bounds getBounds( )
	{
		Bounds bo, boFull = null;
		PrimitiveRenderEvent pre;
		double dDelta;

		for ( int i = 0; i < getElementCount( ); i++ )
		{
			pre = getElement( i );
			try
			{
				bo = pre.getBounds( );
				if ( i == 0 )
				{
					boFull = (Bounds) EcoreUtil.copy( bo );
				}
				else
				{
					if ( bo.getLeft( ) < boFull.getLeft( ) )
					{
						dDelta = boFull.getLeft( ) - bo.getLeft( );
						boFull.setLeft( boFull.getLeft( ) - dDelta );
						boFull.setWidth( boFull.getWidth( ) + dDelta );
					}
					if ( bo.getTop( ) < boFull.getTop( ) )
					{
						dDelta = boFull.getTop( ) - bo.getTop( );
						boFull.setTop( boFull.getTop( ) - dDelta );
						boFull.setHeight( boFull.getHeight( ) + dDelta );
					}
					if ( bo.getLeft( ) + bo.getWidth( ) > boFull.getLeft( )
							+ boFull.getWidth( ) )
					{
						dDelta = bo.getLeft( )
								+ bo.getWidth( )
								- ( boFull.getLeft( ) + boFull.getWidth( ) );
						boFull.setWidth( boFull.getWidth( ) + dDelta );
					}
					if ( bo.getTop( ) + bo.getHeight( ) > boFull.getTop( )
							+ boFull.getHeight( ) )
					{
						dDelta = bo.getTop( )
								+ bo.getHeight( )
								- ( boFull.getTop( ) + boFull.getHeight( ) );
						boFull.setHeight( boFull.getHeight( ) + dDelta );
					}
				}
			}
			catch ( ChartException ufex )
			{
				logger.log( ufex );
			}
		}
		return boFull;
	}

	/**
	 * @return Returns the outline.
	 */
	public final LineAttributes getOutline( )
	{
		return lia;
	}

	/**
	 * @param outline
	 *            The outline to set.
	 */
	public final void setOutline( LineAttributes outline )
	{
		this.lia = outline;
	}
}