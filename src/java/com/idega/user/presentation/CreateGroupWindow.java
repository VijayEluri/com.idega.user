package com.idega.user.presentation;

import com.idega.idegaweb.*;
import com.idega.user.app.ToolbarElement;
import com.idega.presentation.*;
import com.idega.builder.business.BuilderLogic;
import com.idega.builder.data.IBDomain;
import com.idega.business.IBOLookup;
import com.idega.data.IDOLookup;
import com.idega.event.IWActionListener;
import com.idega.event.IWPresentationState;
import com.idega.idegaweb.presentation.IWAdminWindow;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextArea;
import com.idega.presentation.ui.TextInput;
import com.idega.user.business.GroupBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.GroupDomainRelationType;
import com.idega.user.data.GroupDomainRelationTypeHome;
import com.idega.user.data.GroupType;
import com.idega.user.data.GroupTypeHome;
import com.idega.user.event.CreateGroupEvent;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;

/**
 *
 * <p>Title: idegaWeb User</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: idega Software</p>
 * @author <a href="gummi@idega.is">Gu�mundur �g�st S�mundsson</a>
 * @version 1.0
 */

public class CreateGroupWindow extends IWAdminWindow implements StatefullPresentation, ToolbarElement{

  private static final String IW_BUNDLE_IDENTIFIER  = "com.idega.user";
  private StatefullPresentationImplHandler _stateHandler = null;
  private CreateGroupEvent _createEvent;


  public CreateGroupWindow() {
    _stateHandler = new StatefullPresentationImplHandler();
    _stateHandler.setPresentationStateClass(CreateGroupWindowPS.class);
    setWidth(320);
    setHeight(210);
    setScrollbar(false);
    this.getLocation().setApplicationClass(CreateGroupWindow.class);
    this.getLocation().isInPopUpWindow(true);
  }

  public void initializeInMain(IWContext iwc){
    this.addIWActionListener((IWActionListener)this.getPresentationState(iwc));
  }

  public void main(IWContext iwc) throws Exception {

//    this.debugParameters(iwc);

    CreateGroupWindowPS _ps = (CreateGroupWindowPS)this.getPresentationState(iwc);

    if (_ps.doClose()) {
      close();
      _ps.doneClosing();
    } else {
      _createEvent = new CreateGroupEvent();
      _createEvent.setSource(this.getLocation());

      IWResourceBundle iwrb = iwc.getApplication().getBundle(BuilderLogic.IW_BUNDLE_IDENTIFIER).getResourceBundle(iwc);
      Form form = new Form();
      form.addEventModel(_createEvent);

      setTitle(iwrb.getLocalizedString("create_new_group","Create a new Group"));
      addTitle(iwrb.getLocalizedString("create_new_group","Create a new Group"),IWConstants.BUILDER_FONT_STYLE_TITLE);

      add(form);
      Table tab = new Table(2,6);
      tab.setColumnAlignment(1,"right");
      tab.setColumnVerticalAlignment(1,"top");
      tab.setWidth(1,"130");
      tab.setCellspacing(3);
      tab.setAlignment(2,6,"right");
      form.add(tab);
      TextInput inputName = new TextInput(_createEvent.getIONameForName());
      inputName.setLength(28);
      inputName.setStyleAttribute(IWConstants.BUILDER_FONT_STYLE_INTERFACE);
      Text inputText = new Text();

      inputText.setText(iwrb.getLocalizedString("group_name","Group name")+":");

      inputText.setFontStyle(IWConstants.BUILDER_FONT_STYLE_LARGE);
      tab.add(inputText,1,1);
      tab.add(inputName,2,1);

      TextArea descriptionTextArea = new TextArea(_createEvent.getIONameForDescription());
      descriptionTextArea.setHeight(4);
      descriptionTextArea.setStyleAttribute(IWConstants.BUILDER_FONT_STYLE_INTERFACE);

      Text descText = new Text(iwrb.getLocalizedString("group_description","Description")+":");
      descText.setFontStyle(IWConstants.BUILDER_FONT_STYLE_LARGE);
      tab.add(descText,1,2);
      tab.add(descriptionTextArea,2,2);



      GroupChooser groupChooser = getGroupChooser(_createEvent.getIONameForParentID(),iwc);
  //    if (!type.equals(IBPageHelper.TEMPLATE)) {
  //      if (topLevelString == null) {
          Text createUnderText = new Text(iwrb.getLocalizedString("parent_group","Create group under")+":");
                createUnderText.setFontStyle(IWConstants.BUILDER_FONT_STYLE_LARGE);

          Layer layer = new Layer();
          layer.add(createUnderText);
          layer.setNoWrap();
          tab.add(layer,1,3);
          tab.add(groupChooser,2,3);
  //      }
  //    }




      DropdownMenu mnu = new DropdownMenu(_createEvent.getIONameForGroupType());
      try {
        GroupTypeHome gtHome = (GroupTypeHome)IDOLookup.getHome(GroupType.class);
        Collection types = gtHome.findVisibleGroupTypes();
        Iterator iter = types.iterator();
        while (iter.hasNext()) {
          GroupType item = (GroupType)iter.next();
          String value = item.getType();
          String name = item.getType(); //item.getName();
          mnu.addMenuElement(value,name);
        }
      }
      catch (RemoteException ex) {
        throw new EJBException(ex);
      }
  //    mnu.setSelectedElement(type);
      mnu.setStyleAttribute(IWConstants.BUILDER_FONT_STYLE_INTERFACE);

      Text typeText = new Text(iwrb.getLocalizedString("select_type","Select type")+":");
      typeText.setFontStyle(IWConstants.BUILDER_FONT_STYLE_LARGE);
      tab.add(typeText,1,4);
      tab.add(mnu,2,4);

      SubmitButton button = new SubmitButton(iwrb.getLocalizedImageButton("save","Save"),_createEvent.getIONameForCommit());
      SubmitButton close = new SubmitButton(iwrb.getLocalizedImageButton("close","Close"),_createEvent.getIONameForCancel());
      tab.add(close,2,6);
      tab.add(Text.getNonBrakingSpace(),2,6);
      tab.add(button,2,6);

    }
  }

  /*
   *
   */
  private GroupChooser getGroupChooser(String name, IWContext iwc) {
    GroupChooser chooser = new GroupChooser(name);
    chooser.setInputStyle(IWConstants.BUILDER_FONT_STYLE_INTERFACE);

    try {
      	IBDomain domain = iwc.getDomain();
        chooser.setSelectedNode(new GroupTreeNode(domain));
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    return(chooser);
  }



  public String getBundleIdentifier() {
    return IW_BUNDLE_IDENTIFIER;
  }



  public Class getPresentationStateClass(){
    return _stateHandler.getPresentationStateClass();
  }

  public IWPresentationState getPresentationState(IWUserContext iwuc){
    return _stateHandler.getPresentationState(this,iwuc);
  }

  public StatefullPresentationImplHandler getStateHandler(){
    return _stateHandler;
  }

  public Image getButtonImage(IWContext iwc){
    IWBundle bundle = this.getBundle(iwc);
    return bundle.getImage("create_group.gif","Create group");
  }

  public String getName(IWContext iwc){
    IWResourceBundle rBundle = this.getBundle(iwc).getResourceBundle(iwc);
    return rBundle.getLocalizedString("create_group","Create group");
  }

  public PresentationObject getPresentationObject(IWContext iwc){
    return this;
  }

}