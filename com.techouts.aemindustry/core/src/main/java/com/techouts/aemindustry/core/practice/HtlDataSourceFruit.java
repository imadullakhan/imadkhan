package com.techouts.aemindustry.core.practice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;

public class HtlDataSourceFruit extends WCMUsePojo {

	@Override
	public void activate() throws Exception {
		final ResourceResolver resolver = getResource().getResourceResolver();

		// Creating the Map instance to insert the countries
		final Map<String, String> fruits = new LinkedHashMap<String, String>();

		fruits.put("orange", "Orange");
		fruits.put("apple", "Apple");
		fruits.put("pear", "Pear");
		fruits.put("banana", "Banana");
		fruits.put("grapes", "Grapes");
		fruits.put("imadulla", "Imadulla");

		@SuppressWarnings("unchecked")

		// Creating the Datasource Object for populating the drop-down control.
		DataSource ds = new SimpleDataSource(new TransformIterator(fruits.keySet().iterator(), new Transformer() {

			@Override

			// Transforms the input object into output object
			public Object transform(Object o) {
				String fruit = (String) o;

				// Allocating memory to Map
				ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());

				// Populate the Map
				vm.put("value", fruit);
				vm.put("text", fruits.get(fruit));

				return new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm);
			}
		}));

		this.getRequest().setAttribute(DataSource.class.getName(), ds);

	}

}
