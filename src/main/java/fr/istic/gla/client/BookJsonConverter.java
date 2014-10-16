package fr.istic.gla.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import fr.istic.gla.shared.Book;
import fr.istic.gla.shared.BookItf;

public class BookJsonConverter {

	private BookJsonConverter() {
	}
	
	private static BookJsonConverter instance = new BookJsonConverter();
	
	
	  // Instantiate the factory
	  fr.istic.gla.shared.MyFactory factory = GWT.create(fr.istic.gla.shared.MyFactory.class);
	  // In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

	  public BookItf makeBook() {
	    // Construct the AutoBean
	    AutoBean<BookItf> book = factory.book();

	    // Return the Book interface shim
	    return book.as();
	  }

	  String serializeToJson(Book book) {
	    // Retrieve the AutoBean controller
	    AutoBean<BookItf> bean = AutoBeanUtils.getAutoBean(book);

	    return AutoBeanCodex.encode(bean).getPayload();
	  }

	  BookItf deserializeFromJson(String json) {
	    AutoBean<BookItf> bean = AutoBeanCodex.decode(factory, BookItf.class, json);
	    return bean.as();
	  }

	public static BookJsonConverter getInstance() {
		return instance;
	}
}
