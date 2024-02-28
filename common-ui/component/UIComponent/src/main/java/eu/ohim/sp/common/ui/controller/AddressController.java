/*******************************************************************************
 * * $Id:: FeeController.java 50925 2012-11-15 17:10:35Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.service.interfaces.AddressServiceInterface;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class AddressController {
	private static final Logger LOGGER = Logger.getLogger(AddressController.class);

	@Autowired
	private AddressServiceInterface addressServiceInterface;

	@PreAuthorize("hasRole('Address_Autocomplete')")
	@RequestMapping(value = "autocompleteAddress", headers = "Accept=application/json", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String autocompleteAddressService(@RequestParam(value = "args") String text) {
		LOGGER.info("START search");

		List<AddressForm> address = addressServiceInterface.addressAutoComplete("tm-renewal", text);

		ObjectMapper mapper = new ObjectMapper();

		String results = null;
		try {
			results = mapper.writeValueAsString(address);
		} catch (JsonGenerationException e) {
			LOGGER.error(e);
		} catch (JsonMappingException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}

		LOGGER.info("END search");
		return results;
	}

}
