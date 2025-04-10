/**
 *  Copyright (c) 2019-2021 EclipseSource and others.
 * 
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v. 2.0 which is available at
 *  https://www.eclipse.org/legal/epl-2.0.
 * 
 *  This Source Code may also be made available under the following Secondary
 *  Licenses when the conditions for such availability set forth in the Eclipse
 *  Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *  with the GNU Classpath Exception which is available at
 *  https://www.gnu.org/software/classpath/license.html.
 *  SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 *  
 */
package com.gr72s.glsp.example.workflow.wfgraph.util;

import com.gr72s.glsp.example.workflow.wfgraph.*;

import com.gr72s.glsp.graph.GArgumentable;
import com.gr72s.glsp.graph.GBoundsAware;
import com.gr72s.glsp.graph.GCompartment;
import com.gr72s.glsp.graph.GEdge;
import com.gr72s.glsp.graph.GEdgeLayoutable;
import com.gr72s.glsp.graph.GLayoutable;
import com.gr72s.glsp.graph.GLayouting;
import com.gr72s.glsp.graph.GModelElement;
import com.gr72s.glsp.graph.GNode;
import com.gr72s.glsp.graph.GResizable;
import com.gr72s.glsp.graph.GShapeElement;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.gr72s.glsp.example.workflow.wfgraph.WfgraphPackage
 * @generated
 */
public class WfgraphAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static WfgraphPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WfgraphAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = WfgraphPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WfgraphSwitch<Adapter> modelSwitch =
		new WfgraphSwitch<Adapter>() {
			@Override
			public Adapter caseActivityNode(ActivityNode object) {
				return createActivityNodeAdapter();
			}
			@Override
			public Adapter caseTaskNode(TaskNode object) {
				return createTaskNodeAdapter();
			}
			@Override
			public Adapter caseIcon(Icon object) {
				return createIconAdapter();
			}
			@Override
			public Adapter caseWeightedEdge(WeightedEdge object) {
				return createWeightedEdgeAdapter();
			}
			@Override
			public Adapter caseCategory(Category object) {
				return createCategoryAdapter();
			}
			@Override
			public Adapter caseGArgumentable(GArgumentable object) {
				return createGArgumentableAdapter();
			}
			@Override
			public Adapter caseGModelElement(GModelElement object) {
				return createGModelElementAdapter();
			}
			@Override
			public Adapter caseGBoundsAware(GBoundsAware object) {
				return createGBoundsAwareAdapter();
			}
			@Override
			public Adapter caseGLayoutable(GLayoutable object) {
				return createGLayoutableAdapter();
			}
			@Override
			public Adapter caseGResizable(GResizable object) {
				return createGResizableAdapter();
			}
			@Override
			public Adapter caseGShapeElement(GShapeElement object) {
				return createGShapeElementAdapter();
			}
			@Override
			public Adapter caseGEdgeLayoutable(GEdgeLayoutable object) {
				return createGEdgeLayoutableAdapter();
			}
			@Override
			public Adapter caseGLayouting(GLayouting object) {
				return createGLayoutingAdapter();
			}
			@Override
			public Adapter caseGNode(GNode object) {
				return createGNodeAdapter();
			}
			@Override
			public Adapter caseGCompartment(GCompartment object) {
				return createGCompartmentAdapter();
			}
			@Override
			public Adapter caseGEdge(GEdge object) {
				return createGEdgeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.example.workflow.wfgraph.ActivityNode <em>Activity Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.example.workflow.wfgraph.ActivityNode
	 * @generated
	 */
	public Adapter createActivityNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.example.workflow.wfgraph.TaskNode <em>Task Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.example.workflow.wfgraph.TaskNode
	 * @generated
	 */
	public Adapter createTaskNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.example.workflow.wfgraph.Icon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.example.workflow.wfgraph.Icon
	 * @generated
	 */
	public Adapter createIconAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.example.workflow.wfgraph.WeightedEdge <em>Weighted Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.example.workflow.wfgraph.WeightedEdge
	 * @generated
	 */
	public Adapter createWeightedEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.example.workflow.wfgraph.Category <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.example.workflow.wfgraph.Category
	 * @generated
	 */
	public Adapter createCategoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.graph.GArgumentable <em>GArgumentable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.graph.GArgumentable
	 * @generated
	 */
	public Adapter createGArgumentableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.graph.GModelElement <em>GModel Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.graph.GModelElement
	 * @generated
	 */
	public Adapter createGModelElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.graph.GBoundsAware <em>GBounds Aware</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.graph.GBoundsAware
	 * @generated
	 */
	public Adapter createGBoundsAwareAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.graph.GLayoutable <em>GLayoutable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.graph.GLayoutable
	 * @generated
	 */
	public Adapter createGLayoutableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.graph.GResizable <em>GResizable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.graph.GResizable
	 * @generated
	 */
	public Adapter createGResizableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.graph.GShapeElement <em>GShape Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.graph.GShapeElement
	 * @generated
	 */
	public Adapter createGShapeElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.graph.GEdgeLayoutable <em>GEdge Layoutable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.graph.GEdgeLayoutable
	 * @generated
	 */
	public Adapter createGEdgeLayoutableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.graph.GLayouting <em>GLayouting</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.graph.GLayouting
	 * @generated
	 */
	public Adapter createGLayoutingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.graph.GNode <em>GNode</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.graph.GNode
	 * @generated
	 */
	public Adapter createGNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.graph.GCompartment <em>GCompartment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.graph.GCompartment
	 * @generated
	 */
	public Adapter createGCompartmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.gr72s.glsp.graph.GEdge <em>GEdge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.gr72s.glsp.graph.GEdge
	 * @generated
	 */
	public Adapter createGEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //WfgraphAdapterFactory
