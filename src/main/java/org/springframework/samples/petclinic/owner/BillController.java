package org.springframework.samples.petclinic.owner;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillController {
	
	@Autowired
	private BillService billServ;
	
	@Autowired
	private VisitRepository visits;
	
    @RequestMapping(value = "/bills/", method = RequestMethod.GET)
    public Collection<Bill> read() {
        return this.billServ.findAll();
    }
    
    @RequestMapping(value = "/bills/{id}", method = RequestMethod.GET)
    public Bill read(@PathVariable(value = "id") int id) {
        return this.billServ.findById(id);
    }
    
    @RequestMapping(value = "/bills", method = RequestMethod.GET, params = {"filter"})
    public ResponseEntity<Collection<Bill>> readFiltered(@RequestParam(value = "filter") String filter) {
    	if (filter.contentEquals("pagadas")) {
    		return ResponseEntity.status(HttpStatus.OK).body(this.billServ.getBillByVisitNotNull());
    	} else if (filter.contentEquals("no_pagadas")) {
    		return ResponseEntity.status(HttpStatus.OK).body(this.billServ.getBillByVisitNull());
    	} else {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	}
    }
    
    @RequestMapping(value = "/bills/{idBill}/visit/{idVisit}", method = RequestMethod.GET)
    public ResponseEntity<Visit> readVisitDetails(@PathVariable(value = "idBill") int idBill,
    		@PathVariable(value = "idVisit") int idVisit) {
        Visit visit = this.billServ.visitDetails(idBill, idVisit);
        if (visit != null) {
        	return ResponseEntity.status(HttpStatus.OK).body(this.billServ.visitDetails(idBill, idVisit));
        } else {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @RequestMapping(value = "/bills/{idBill}/visit/{idVisit}", method = RequestMethod.POST)
    public ResponseEntity<Bill> addVisit(@PathVariable(value = "idBill") int idBill,
    		@PathVariable(value = "idVisit") int idVisit) {
    	Bill bill = this.billServ.addVisit(idBill, idVisit);
    	if (bill != null) {
    		return ResponseEntity.status(HttpStatus.OK).body(bill);
    	} else {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	}
    }
    
    @RequestMapping(value = "/bills/*", method = RequestMethod.POST)
    public ResponseEntity<Bill> create(@RequestBody Bill bill) {
    	if (bill != null) {
    		if (this.billServ.notExists(bill.getId())) {
    	    	return ResponseEntity.status(HttpStatus.OK).body(this.billServ.save(bill));
    		} else {
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    		}
    	} else {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	}
    }
    
    
    @RequestMapping(value = "/bills/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") int id) {
        this.billServ.delete(id);
    }
    
    @RequestMapping(value = "/bills/", method = RequestMethod.DELETE)
    public void deleteAll() {
        this.billServ.deleteAll();
    }
    
    @RequestMapping(value = "/bills/{id}", method = RequestMethod.PUT)
    public void update(@RequestBody @Valid Bill bill, @PathVariable(value = "id") int id) {
        this.billServ.update(id, bill);
    }
    
    
    @RequestMapping(value = "/visits", method = RequestMethod.GET, params = {"filter"})
    @ResponseBody
    public ResponseEntity<Collection<Visit>> readVisitsFiltered(@RequestParam(value = "filter") String filter) {
    	if (filter.contentEquals("pagadas")) {
    		return ResponseEntity.status(HttpStatus.OK).body(this.visits.getVisitByBillNotNull());
    	} else if (filter.contentEquals("no_pagadas")) {
    		return ResponseEntity.status(HttpStatus.OK).body(this.visits.getVisitByBillNull());
    	} else {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	}
    }


}
