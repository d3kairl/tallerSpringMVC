package org.springframework.samples.petclinic.owner;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	public Owner findById(int id) {
		return this.ownerRepository.findById(id);
	}
	
	public void save(Owner ow) {
		this.ownerRepository.save(ow);
	}
	
	public Collection<Owner> findByLastName(String name) {
		return this.ownerRepository.findByLastName(name);
	}
}
