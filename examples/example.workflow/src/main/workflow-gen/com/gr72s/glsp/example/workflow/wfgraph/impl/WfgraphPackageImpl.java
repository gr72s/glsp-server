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
package com.gr72s.glsp.example.workflow.wfgraph.impl;

import com.gr72s.glsp.example.workflow.wfgraph.ActivityNode;
import com.gr72s.glsp.example.workflow.wfgraph.Category;
import com.gr72s.glsp.example.workflow.wfgraph.Icon;
import com.gr72s.glsp.example.workflow.wfgraph.TaskNode;
import com.gr72s.glsp.example.workflow.wfgraph.WeightedEdge;
import com.gr72s.glsp.example.workflow.wfgraph.WfgraphFactory;
import com.gr72s.glsp.example.workflow.wfgraph.WfgraphPackage;

import com.gr72s.glsp.graph.GraphPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WfgraphPackageImpl extends EPackageImpl implements WfgraphPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass taskNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iconEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass weightedEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass categoryEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.gr72s.glsp.example.workflow.wfgraph.WfgraphPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private WfgraphPackageImpl() {
		super(eNS_URI, WfgraphFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link WfgraphPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static WfgraphPackage init() {
		if (isInited) return (WfgraphPackage)EPackage.Registry.INSTANCE.getEPackage(WfgraphPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredWfgraphPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		WfgraphPackageImpl theWfgraphPackage = registeredWfgraphPackage instanceof WfgraphPackageImpl ? (WfgraphPackageImpl)registeredWfgraphPackage : new WfgraphPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		GraphPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theWfgraphPackage.createPackageContents();

		// Initialize created meta-data
		theWfgraphPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theWfgraphPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(WfgraphPackage.eNS_URI, theWfgraphPackage);
		return theWfgraphPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getActivityNode() {
		return activityNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getActivityNode_NodeType() {
		return (EAttribute)activityNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTaskNode() {
		return taskNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTaskNode_Name() {
		return (EAttribute)taskNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTaskNode_Expanded() {
		return (EAttribute)taskNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTaskNode_Duration() {
		return (EAttribute)taskNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTaskNode_TaskType() {
		return (EAttribute)taskNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTaskNode_Reference() {
		return (EAttribute)taskNodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIcon() {
		return iconEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getWeightedEdge() {
		return weightedEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWeightedEdge_Probability() {
		return (EAttribute)weightedEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCategory() {
		return categoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCategory_Name() {
		return (EAttribute)categoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WfgraphFactory getWfgraphFactory() {
		return (WfgraphFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		activityNodeEClass = createEClass(ACTIVITY_NODE);
		createEAttribute(activityNodeEClass, ACTIVITY_NODE__NODE_TYPE);

		taskNodeEClass = createEClass(TASK_NODE);
		createEAttribute(taskNodeEClass, TASK_NODE__NAME);
		createEAttribute(taskNodeEClass, TASK_NODE__EXPANDED);
		createEAttribute(taskNodeEClass, TASK_NODE__DURATION);
		createEAttribute(taskNodeEClass, TASK_NODE__TASK_TYPE);
		createEAttribute(taskNodeEClass, TASK_NODE__REFERENCE);

		iconEClass = createEClass(ICON);

		weightedEdgeEClass = createEClass(WEIGHTED_EDGE);
		createEAttribute(weightedEdgeEClass, WEIGHTED_EDGE__PROBABILITY);

		categoryEClass = createEClass(CATEGORY);
		createEAttribute(categoryEClass, CATEGORY__NAME);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		GraphPackage theGraphPackage = (GraphPackage)EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		activityNodeEClass.getESuperTypes().add(theGraphPackage.getGNode());
		taskNodeEClass.getESuperTypes().add(this.getActivityNode());
		iconEClass.getESuperTypes().add(theGraphPackage.getGCompartment());
		weightedEdgeEClass.getESuperTypes().add(theGraphPackage.getGEdge());
		categoryEClass.getESuperTypes().add(this.getActivityNode());

		// Initialize classes, features, and operations; add parameters
		initEClass(activityNodeEClass, ActivityNode.class, "ActivityNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getActivityNode_NodeType(), ecorePackage.getEString(), "nodeType", null, 0, 1, ActivityNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(taskNodeEClass, TaskNode.class, "TaskNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTaskNode_Name(), ecorePackage.getEString(), "name", null, 0, 1, TaskNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskNode_Expanded(), ecorePackage.getEBoolean(), "expanded", "false", 1, 1, TaskNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskNode_Duration(), ecorePackage.getEInt(), "duration", "0", 1, 1, TaskNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskNode_TaskType(), ecorePackage.getEString(), "taskType", null, 0, 1, TaskNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskNode_Reference(), ecorePackage.getEString(), "reference", null, 0, 1, TaskNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iconEClass, Icon.class, "Icon", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(weightedEdgeEClass, WeightedEdge.class, "WeightedEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWeightedEdge_Probability(), ecorePackage.getEString(), "probability", null, 0, 1, WeightedEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(categoryEClass, Category.class, "Category", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCategory_Name(), ecorePackage.getEString(), "name", null, 0, 1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //WfgraphPackageImpl
