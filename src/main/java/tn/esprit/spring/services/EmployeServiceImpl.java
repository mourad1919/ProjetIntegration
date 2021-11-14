package tn.esprit.spring.services;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	private static final Logger LOG = LogManager.getLogger(EmployeServiceImpl.class);

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	@Override
	public int addOrUpdateEmploye(Employe employe) {

		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {

		LOG.info(MessageFormat.format("Employe Id,{0}",employeId));

		Employe employe = employeRepository.findById(employeId).get();
		employe.setEmail(email);
		employeRepository.save(employe);

	}

	@Transactional
	public void affecterEmployeADepartement(int employeId, int depId) {

		LOG.info(MessageFormat.format("Department Id {0}", depId));
		LOG.info(MessageFormat.format("Employe Id {0}", employeId));
		Departement depManagedEntity = null;
		Employe employeManagedEntity = null;
		Optional<Employe> empl = employeRepository.findById(employeId);
		Optional<Departement> dep = deptRepoistory.findById(depId);
		if (dep.isPresent() && empl.isPresent()) {
			depManagedEntity = dep.get();
			LOG.debug(MessageFormat.format("Department Id  {0}", depId));
			employeManagedEntity = empl.get();
			LOG.debug(MessageFormat.format("Employe Id, {0}", employeId));

			if (depManagedEntity.getEmployes() == null) {

				List<Employe> employes = new ArrayList<>();
				employes.add(employeManagedEntity);
				depManagedEntity.setEmployes(employes);
			} else {

				depManagedEntity.getEmployes().add(employeManagedEntity);
				LOG.info(MessageFormat.format("Employe added to department{0}", employeId));

			}

			// à ajouter?
			deptRepoistory.save(depManagedEntity);
			LOG.info(MessageFormat.format("Department{0}", depManagedEntity));
		}

	}

	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		LOG.info(MessageFormat.format("Department Id{0}", depId));
		LOG.info(MessageFormat.format("Employe Id , {0}", employeId));
		Departement dep = null;
		Optional<Employe> empl = employeRepository.findById(employeId);
		Optional<Departement> departement = deptRepoistory.findById(employeId);
		if (departement.isPresent() && empl.isPresent()) {
			dep = departement.get();
			LOG.debug(MessageFormat.format("Department Id{0}", depId));

			int employeNb = dep.getEmployes().size();
			for (int index = 0; index < employeNb; index++) {
				if (dep.getEmployes().get(index).getId() == employeId) {
					dep.getEmployes().remove(index);
					LOG.debug(MessageFormat.format("Employe Id  ,{0}", employeId));

					break;// a revoir
				}
			}
		}
	}

	// Tablesapce (espace disque)

	public int ajouterContrat(Contrat contrat) {
		LOG.info(MessageFormat.format("Contrat{0}", contrat));

		contratRepoistory.save(contrat);
		LOG.debug(MessageFormat.format("Contrat saved{0}", contrat));
		LOG.debug(MessageFormat.format("Contrat Reference{0}", contrat.getReference()));
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		LOG.info(MessageFormat.format("Employe Id {0}", employeId));
		LOG.info(MessageFormat.format("Contrat Id {0}", contratId));
		Contrat contratManagedEntity = null;
		Employe employeManagedEntity = null;
		Optional<Employe> empl = employeRepository.findById(employeId);
		Optional<Contrat> contrat = contratRepoistory.findById(employeId);
		if (contrat.isPresent() && empl.isPresent()) {
			contratManagedEntity = contrat.get();
			LOG.debug(MessageFormat.format("Contrat Id{0}", contratId));

			employeManagedEntity = empl.get();
			LOG.debug(MessageFormat.format("Employe Id{0}", employeId));

			contratManagedEntity.setEmploye(employeManagedEntity);
			LOG.info(MessageFormat.format("Contrat added to Employe{0}", employeId));

			contratRepoistory.save(contratManagedEntity);
			LOG.info(MessageFormat.format("Contrat{0}", contratManagedEntity));
		} else {
			LOG.info("Employe or Contrat doesn't exist");
		}
	}

	public String getEmployePrenomById(int employeId) {
		Employe employe = null;
		Optional<Employe> empl = employeRepository.findById(employeId);
		if (empl.isPresent()) {
			employe = empl.get();
			return employe.getPrenom();
		} else {
			LOG.info("Employe doesn't exist");
			return null;
		}
	}

	public void deleteEmployeById(int employeId) {
		LOG.info(MessageFormat.format("Employe Id{0}", employeId));
		Employe employe = null;
		Optional<Employe> empl = employeRepository.findById(employeId);
		if (empl.isPresent()) {
			employe = empl.get();

			// Desaffecter l'employe de tous les departements
			// c'est le bout master qui permet de mettre a jour
			// la table d'association
			for (Departement dep : employe.getDepartements()) {
				dep.getEmployes().remove(employe);
			}

			employeRepository.delete(employe);
			LOG.debug(MessageFormat.format("Employe Id deleted{0}", employeId));
		} else {
			LOG.info("Employe doesn't exist");
		}
	}

	public void deleteContratById(int contratId) {
		LOG.info(MessageFormat.format("Contrat Id{0}", contratId));
		Optional<Contrat> contrat = contratRepoistory.findById(contratId);
		Contrat contratManagedEntity = null;
		if (contrat.isPresent()) {
			contratManagedEntity = contrat.get();
			contratRepoistory.delete(contratManagedEntity);
			LOG.info(MessageFormat.format("Contrat Id deleted{0}", contratId));
		} else {
			LOG.info("Contrat doesn't exist");

		}
	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}

	public void deleteAllContratJPQL() {
		employeRepository.deleteAllContratJPQL();
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
		return (List<Employe>) employeRepository.findAll();
	}

	@Override
	public int ajouterEmploye(Employe employe) {
		// TODO Auto-generated method stub
		return 0;
	}

}
