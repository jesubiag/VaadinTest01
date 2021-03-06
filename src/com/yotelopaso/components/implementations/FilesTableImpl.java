package com.yotelopaso.components.implementations;

import java.util.Date;

import com.vaadin.ui.Button;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.yotelopaso.components.FilesTable;
import com.yotelopaso.persistence.UserManager;
import com.yotelopaso.presenters.FilesTablePresenter;
import com.yotelopaso.utils.DateUtils;

public class FilesTableImpl extends CustomComponent implements FilesTable {

	private static final long serialVersionUID = 1L;
	
	final private HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
	final private Table filesTable = new Table();
	
	final private VerticalLayout mainLayout = new VerticalLayout();
	final private VerticalLayout fileDetailLayout = new VerticalLayout();
	final private HorizontalLayout botonesLayout = new HorizontalLayout();

	private FilesTablePresenter presenter;
	private String subjectName;
	private String careerName;
	private String type;
	private ItemClickListener parentView;
	private ClickListener parentListener;
	private Button editButton;
	private Button deleteButton;
	private UserManager userService = new UserManager();
	final private Label fileAuthorTitle = new Label("<b>Autor:</b>", ContentMode.HTML);
	final private Label fileDateTitle = new Label("<b>Fecha:</b>", ContentMode.HTML);
	final private Label fileNameTitle = new Label("<b>Nombre:</b>", ContentMode.HTML);
	final private Label fileDescTitle = new Label("<b>Descripción:</b>", ContentMode.HTML);
	final private Label fileAuthor = new Label("", ContentMode.HTML);
	final private Label fileDate = new Label("", ContentMode.HTML);
	final private Label fileName = new Label("", ContentMode.HTML);
	final private Label fileDesc = new Label("", ContentMode.HTML);
	
	public FilesTableImpl(String subjectName, ClickListener parentListener, ItemClickListener parentView, 
			String type, String careerName) {
		this.subjectName = subjectName;
		this.careerName = careerName;
		this.type = type;
		this.presenter = new FilesTablePresenter(this);
		this.parentView = parentView;
		this.parentListener = parentListener;
		setImmediate(true);
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}
	
	private void buildMainLayout() {
		
		hsplit.setSizeFull();
		
		//Seteo los elementos de la tabla
		filesTable.setSizeFull();
		filesTable.setImmediate(true);
		filesTable.addStyleName("components-inside");
		String[] tableIds = {"Nombre", "Autor", "Fecha de creación", "Descripción","ID"};
		filesTable.addContainerProperty(tableIds[0], Link.class, null);
		filesTable.addContainerProperty(tableIds[1], String.class, null);
		filesTable.addContainerProperty(tableIds[2], String.class, null);
		filesTable.addContainerProperty(tableIds[3], String.class, null);
		filesTable.addContainerProperty(tableIds[4], Long.class, null);
		
		filesTable.addStyleName(ValoTheme.TABLE_BORDERLESS);
		filesTable.addStyleName(ValoTheme.TABLE_COMPACT);
		filesTable.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
		filesTable.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		filesTable.setColumnReorderingAllowed(true);
		filesTable.setColumnCollapsingAllowed(true);
		filesTable.setColumnCollapsed(tableIds[3], true);
		filesTable.setColumnCollapsed(tableIds[4], true);
		
		//TODO: cuando seteo esta propiedad, el contenido deja de mostrarse
		//filesTable.setVisibleColumns(new Object[] {tableIds[0], tableIds[1], tableIds[2]});
		fileDetailLayout.setSizeFull();
		
		fileDesc.setSizeFull();
		
		Button newButton = new Button("Subir Archivo");
		newButton.setStyleName("primary");
		newButton.setStyleName("small");
		
		editButton = new Button("Editar ");
		editButton.setStyleName("small");
		editButton.addClickListener(parentListener);
		
		deleteButton = new Button("Eliminar ");
		deleteButton.setStyleName("small");
		deleteButton.addClickListener(parentListener);
		
		botonesLayout.addComponents(editButton,deleteButton);
		botonesLayout.setWidthUndefined();
		
		//fileDetailLayout.addComponent(newButton);
		fileDetailLayout.addComponents(fileNameTitle, fileName);
		fileDetailLayout.addComponents(fileDateTitle, fileDate);
		fileDetailLayout.addComponents(fileAuthorTitle, fileAuthor);
		fileDetailLayout.addComponents(fileDescTitle, fileDesc);
		
		presenter.getData(subjectName, careerName, type);
		
		filesTable.addItemClickListener(parentView);
		filesTable.setData(type);
		
		hsplit.setSplitPosition(23, true);
		hsplit.setLocked(true);
		hsplit.setFirstComponent(filesTable);
		hsplit.setSecondComponent(fileDetailLayout);
		
		mainLayout.setSizeFull();
		mainLayout.addComponent(hsplit);
	}

	@Override
	public void buildTable(String name, String url, String authorName, 
			Date fileDate, Long id, String desc) {
		Link fileLink = new Link(name, new ExternalResource(url));
		fileLink.setTargetName("_blank");
		filesTable.addItem(new Object[] {fileLink, authorName, 
				DateUtils.dateFormat(fileDate), desc, id}, id);
	}
	
	public void buildFileDetailLayout(Long fileId, String authorName, String date, String name, String desc) {
		fileDetailLayout.removeComponent(botonesLayout);
		fileAuthor.setValue(authorName);
		fileDate.setValue(date);
		fileName.setValue(name);
		fileDesc.setValue(desc);
		if (fileAuthor.getValue().equals((userService.getCurrentUser().getUsername()))){
			fileDetailLayout.addComponent(botonesLayout);
			editButton.setData(fileId);
			deleteButton.setData(fileId);
		}
	}
	public HorizontalSplitPanel getHsplit() {
		return hsplit;
	}
	
	public void reload() {
		//filesTable.markAsDirty();
		filesTable.refreshRowCache();
	}

}
