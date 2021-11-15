package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

	@Autowired
	EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	@Override
	public int ajouterEntreprise(Entreprise entreprise) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int ajouterDepartement(Departement dep) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteEntrepriseById(int entrepriseId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteDepartementById(int depId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Entreprise getEntrepriseById(int entrepriseId) {
		// TODO Auto-generated method stub
		return null;
	}

//	public int ajouterEntreprise(Entreprise entreprise) {
//		entrepriseRepoistory.save(entreprise);
//		return entreprise.getId();
//	}
//
//	public int ajouterDepartement(Departement dep) {
//		deptRepoistory.save(dep);
//		return dep.getId();
//	}
//
//	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
//
//		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
//		Departement depManagedEntity = deptRepoistory.findById(depId).get();
//
//		depManagedEntity.setEntreprise(entrepriseManagedEntity);
//		deptRepoistory.save(depManagedEntity);
//
//	}
//
//	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
//		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
//		List<String> depNames = new ArrayList<>();
//		for (Departement dep : entrepriseManagedEntity.getDepartements()) {
//			depNames.add(dep.getName());
//		}
//
//		return depNames;
//	}
//
//	@Transactional
//	public void deleteEntrepriseById(int entrepriseId) {
//		entrepriseRepoistory.delete(entrepriseRepoistory.findById(entrepriseId).get());
//	}
//
//	@Transactional
//	public void deleteDepartementById(int depId) {
//		deptRepoistory.delete(deptRepoistory.findById(depId).get());
//	}
//
//	public Entreprise getEntrepriseById(int entrepriseId) {
//		return entrepriseRepoistory.findById(entrepriseId).get();
//	}

}
