package org.improve.nsk.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.improve.nsk.domain.Product;
import org.improve.nsk.dto.*;
import org.improve.nsk.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by smu8 on 01.07.2015.
 */
@Controller
@RequestMapping("/main")
public class MainController {

    protected static Logger logger = Logger.getLogger("controller");

    @Resource(name = "productService")
    private ProductService productService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getRecords(Model model){
        logger.debug("Received request to show records page");

        // Retrieve all persons
        List<Product> products = productService.getAll();

        // Prepare model object
        List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();

        for (Product product: products) {
            // Create new data transfer object
            ProductDTO dto = new ProductDTO();

            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setCatId(product.getCatId());

            // Add to model list
            productsDTO.add(dto);
        }

        // Add to model
        model.addAttribute("products", productsDTO);

        // This will resolve to /WEB-INF/jsp/records.jsp
        return "records";
    }

    /**
     *  Retrieves the "Add New Record" page
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAdd(Model model) {
        logger.debug("Received request to show add page");

        // Create new Person and add to model
        model.addAttribute("productAttribute", new Product());

        // This will resolve to /WEB-INF/jsp/add-record.jsp
        return "add-record";
    }

    /**
     * Adds a new record
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String postAdd(@ModelAttribute("personAttribute") Product product) {
        logger.debug("Received request to add new record");

        // Delegate to service
        productService.add(product);

        // Redirect to url
        return "redirect:/main/list";
    }
}
