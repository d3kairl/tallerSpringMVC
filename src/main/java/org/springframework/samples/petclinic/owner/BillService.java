package org.springframework.samples.petclinic.owner;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Service;

@Service
public class BillService {
	
	@Autowired
	private BillRepository billRepo;
	
	@Autowired
	private VisitRepository visitRepo;
	
	public Bill findById(int id) {
		return this.billRepo.findOne(id);
	}
	
	public Collection<Bill> findAll() {
		return this.billRepo.findAll();
	}
	
	public List<Bill> getBillByVisitNotNull() {
		return this.billRepo.getBillByVisitNotNull();
	}
	
	public List<Bill> getBillByVisitNull() {
		return this.billRepo.getBillByVisitNull();
	}
	
	public Bill save(Bill bill) {
		billRepo.save(bill);
		return bill;
	}
	
	public void delete(int id) {
		billRepo.delete(id);
	}
	
	public void deleteAll() {
		billRepo.deleteAll();
	}

	public void update(int id, Bill bill) {
		Bill old = billRepo.findOne(id);

		if (old != null) {
			old = bill;
			billRepo.save(old);
		}
	}
	
	public boolean notExists(int id) {
		return !billRepo.exists(id);
	}
	
	public Visit visitDetails(int idBill, int idVisit) {
		Visit visit = this.billRepo.findOne(idBill).getVisit();
		if (visit.getId() == idVisit) {
			return visit;
		} else {
			return null;
		}
	}
	
	public Bill addVisit(int idBill, int idVisit) {
			if (visitRepo.findById(idVisit) != null) { // Si existe
				Bill bill_modif = billRepo.findOne(idBill);
				bill_modif.setVisit(visitRepo.findById(idVisit));
				this.save(bill_modif);
				return bill_modif;
			} else { // Si no existe
				return null;
			}
	}
}
