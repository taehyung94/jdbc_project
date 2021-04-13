package application.controllers;

import javafx.beans.property.*;

public class TableRowDataModel {
	    private final StringProperty title = new SimpleStringProperty();
	    private final StringProperty writer = new SimpleStringProperty();
	    private final IntegerProperty view = new SimpleIntegerProperty();
	    private final IntegerProperty id = new SimpleIntegerProperty();
	    private final IntegerProperty number = new SimpleIntegerProperty() ;

	    public IntegerProperty numberProperty() {
	        return number ;
	    }
	    public StringProperty titleProperty() {
	        return title ;
	    }
	    public StringProperty writerProperty() {
	        return writer ;
	    }
	    public IntegerProperty viewProperty() {
	        return view ;
	    }
	    public IntegerProperty idProperty() {
	        return id ;
	    }

	    public final int getNumber() {
	        return numberProperty().get();
	    }

	    public final void setNumber(int number) {
	        numberProperty().set(number);
	    }
	    public final String getTitle() {
	        return titleProperty().get();
	    }

	    public final void setTitle(String title) {
	        titleProperty().set(title);
	    }
	    public final String getWriter() {
	        return writerProperty().get();
	    }

	    public final void setWriter(String writer) {
	        writerProperty().set(writer);
	    }
	    public final int getView() {
	        return viewProperty().get();
	    }

	    public final void setView(int view) {
	        viewProperty().set(view);
	    }
	    
	    public final int getId() {
	        return idProperty().get();
	    }

	    public final void setId(int id) {
	        idProperty().set(id);
	    }
	    
	    public TableRowDataModel(int number, String title, String writer, int view, int id) {
	        setNumber(number);
	        setTitle(title);
	        setWriter(writer);
	        setView(view);
	        setId(id);
	    }
	    
	    
	 
	    

//		public IntegerProperty numberProperty() {
//	        return number;
//	    }
//	    public StringProperty titleProperty() {
//	        return title;
//	    }
//	    public StringProperty writerProperty() {
//	        return writer;
//	    }
//	    public IntegerProperty viewProperty() {
//	    	return view;
//	    }
//	    public IntegerProperty idProperty() {
//	    	return id;
//	    }
	}
