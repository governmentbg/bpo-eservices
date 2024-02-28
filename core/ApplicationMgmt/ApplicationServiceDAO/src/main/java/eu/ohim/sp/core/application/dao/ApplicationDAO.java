/*
 *  ApplicationServiceDao:: ApplicationDAO 16/10/13 16:43 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.application.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.criteria.*;

import eu.ohim.sp.core.application.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import eu.ohim.sp.common.service.Dao;

/**
 * Utility class that manages all the persistence of DraftApplication, Status
 * 
 * @author karalch
 * 
 */
@Dao
public class ApplicationDAO {

	private final Logger logger = Logger.getLogger(ApplicationDAO.class);

	@PersistenceContext(name = "ApplicationDomain", unitName = "sp-jpa-application", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;

	/**
	 * Persists the Draft Application
	 * 
	 * @param draftApplication
	 */
	public void saveDraftApplication(eu.ohim.sp.core.application.DraftApplication draftApplication) {
		entityManager.persist(draftApplication);
	}

	/**
	 * Gets the draft application by providing its provisionalId
	 * 
	 * @param provisionalId
	 * @return the draft application associated with this provisionalId
	 */
	public eu.ohim.sp.core.application.DraftApplication findDraftApplicationByProvisionalId(
			String provisionalId) {
		try {
			return (eu.ohim.sp.core.application.DraftApplication) entityManager
					.createNamedQuery(
							"DraftApplication.findApplicationByProvisionalId")
					.setParameter("provisionalId", provisionalId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public TypeApplication getTypeApplication(Long code) {
		try {
			return (TypeApplication) entityManager
					.createNamedQuery("TypeApplication.getType")
					.setParameter("code", code).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public TypeApplication getTypeApplicationByDescription(String description) {
		try {
			return (TypeApplication) entityManager
					.createNamedQuery("TypeApplication.getTypeByDescription")
					.setParameter("description", description).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}

	public Status getStatus(Integer status) {
		try {
			return (Status) entityManager
					.createNamedQuery("DraftStatus.getStatus")
					.setParameter("code", status).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Status getStatusByDescription(String description) {
		try {
			return (Status) entityManager
					.createNamedQuery("DraftStatus.getStatusByDescription")
					.setParameter("description", description).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

    /**
     *
     * @param draftApplication
     * @return
     */
	public eu.ohim.sp.core.application.DraftApplication updateStatus(eu.ohim.sp.core.domain.application.DraftApplication draftApplication) {

		logger.debug("	>>> UpdateStatus START");
		
		eu.ohim.sp.core.application.DraftApplication draftDAO = null;
		if (draftApplication != null) {
            // Retrieve by the provisionalId
			if (StringUtils.isNotBlank(draftApplication.getProvisionalId())) {
				draftDAO = findDraftApplicationByProvisionalId(draftApplication.getProvisionalId());
			} 

            // if it is null, create a new instance of DraftApplication
			if (draftDAO == null) {
				draftDAO = new eu.ohim.sp.core.application.DraftApplication();
                draftDAO.setDtCreated(draftApplication.getDtCreated()==null?new Date():draftApplication.getDtCreated());
			}
	
			if (draftDAO != null) {
				if (StringUtils.isNotEmpty(draftApplication.getTyApplication())) {
					//Call to TypeApplication.getTypeByDescription
					TypeApplication typeApplication = getTypeApplicationByDescription(draftApplication.getTyApplication());
					if (typeApplication != null) {
						draftDAO.setTyApplication(typeApplication);
					}
				}
				
				//This field cannot be null, TyApplicayion should be set when you create the record for the first time

                if (StringUtils.isNotBlank(draftApplication.getApplicationId())) {
					draftDAO.setApplicationId(draftApplication.getApplicationId());
				}

				if (StringUtils.isNotBlank(draftApplication.getApplicationReference())) {
					draftDAO.setApplicationReference(draftApplication.getApplicationReference());
				}

                //DateCreated cannot be changed, it should be set when the application record is created for first time

				if (StringUtils.isNotBlank(draftApplication.getOffice())) {
					draftDAO.setOffice(draftApplication.getOffice());
				}
	
				if (StringUtils.isNotBlank(draftApplication.getPaymentId())) {
					draftDAO.setPaymentId(draftApplication.getPaymentId());
				}
				
				if (StringUtils.isNotBlank(draftApplication.getProvisionalId())) {
					draftDAO.setProvisionalId(draftApplication.getProvisionalId());
				}
				
				if (StringUtils.isNotBlank(draftApplication.getUsername())) {
					draftDAO.setUsername(draftApplication.getUsername());
				}

				if(draftApplication.geteCorrespondence() != null){
					draftDAO.seteCorrespondence(draftApplication.geteCorrespondence());
				}

				if(draftApplication.getTitleApplication() != null){
					if(draftApplication.getTitleApplication().length() >4000){
						logger.warn("TRUNCATING APPLICATION TITLE TO 4000 chars");
						draftDAO.setTitleApplication(draftApplication.getTitleApplication().substring(0, 4000));
					} else {
						draftDAO.setTitleApplication(draftApplication.getTitleApplication());
					}
				}

				if(draftApplication.getAppSubtype() != null){
					draftDAO.setAppSubtype(draftApplication.getAppSubtype());
				}
				
				//The current status. If is not this one will be inside Set<DraftStatus>
				DraftStatus currentStatus = null;
				
				if (draftApplication.getCurrentStatus() != null) {
					eu.ohim.sp.core.domain.application.DraftStatus draftStatusCore = draftApplication.getCurrentStatus();
					if (draftStatusCore.getStatus() != null && draftStatusCore.getStatus().getCode() != null) {
						if (draftDAO.getCurrentStatus() == null || (draftDAO.getCurrentStatus().getCode().intValue() != draftStatusCore.getStatus().getCode().intValue())) {
							Status status = getStatus(draftStatusCore.getStatus().getCode());

							currentStatus = new DraftStatus(status);
							currentStatus.setDraftApplication(draftDAO);

							if (StringUtils.isNotBlank(draftStatusCore.getMessage())) {
								currentStatus.setMessage(draftStatusCore.getMessage());
							}
							if (draftStatusCore.getModifiedDate() != null) {
								currentStatus.setModifiedDate(draftStatusCore.getModifiedDate());
							}
							draftDAO.setCurrentStatus(status);
						}

					}					
				}
				
				//Add currentStatus to the end of the Set<DraftStatus>
				if (currentStatus != null) {
					draftDAO.getStatuses().add(currentStatus);
				}

				if (draftApplication.getCurrentSignStatus() != null && draftApplication.getCurrentSignStatus().getId() == null) {
					eu.ohim.sp.core.domain.application.DraftSignStatus draftStatusCore = draftApplication.getCurrentSignStatus();
					DraftSignStatus draftStatus = new DraftSignStatus();
					draftStatus.setId(draftStatusCore.getId());
					draftStatus.setModifiedDate(draftStatusCore.getModifiedDate());
					draftStatus.setDraftApplication(draftDAO);
					draftStatus.setMessage(draftStatusCore.getMessage());
					draftStatus.setSignStatus(draftStatusCore.getSignStatus());

					draftDAO.setCurrentSignStatus(draftStatus);

					draftDAO.getSignStatuses().add(draftStatus);
				}
				
				logger.debug("		>>> UpdateStatus SAVEDRAFT with ID: " + draftDAO.getId());
				
			}

            saveDraftApplication(draftDAO);
            
			logger.debug("		- DraftpApplicationDAO id: " + draftDAO.getId());
        }
		
		logger.debug("	>>> UpdateStatus END");
		
		return draftDAO;
	}

	/**
	 * Gets the current status of the draft application
	 * 
	 * @param provisionalId
	 *            the id of the application
	 * @return the current status of the application
	 */
	@SuppressWarnings("unchecked")
	public DraftStatus getStatusById(String provisionalId) {
		List<DraftStatus> statusList = entityManager
				.createNamedQuery("Status.getStatus")
				.setParameter("applicationId", provisionalId).getResultList();
		if (statusList == null || statusList.size() == 0) {
			logger.debug("Empty List : not saved status for : " + provisionalId);
			return null;
		} else {
			logger.debug("Status for " + provisionalId + " : "
					+ statusList.get(0));
			return statusList.get(0);
		}
	}

    /**
     * Retrieves all the application from database for the given parameters
     * @param office
     * @param module
     * @param searchCriteria
     * @param pageIndex
     * @param pageSize
     * @param sortDirection
     * @param sortProperty
     * @return
     */
	@SuppressWarnings("unchecked")
    public List<eu.ohim.sp.core.domain.application.DraftApplication> getDraftApplications(String office, String module,
                                                                                           Map<String, String> searchCriteria, Integer pageIndex, Integer pageSize,
                                                                                           Boolean sortDirection, String sortProperty) {

        List<eu.ohim.sp.core.domain.application.DraftApplication> draftApplicationList = new ArrayList<eu.ohim.sp.core.domain.application.DraftApplication>();
        Query query = null;

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<eu.ohim.sp.core.application.DraftApplication> criteria = builder.createQuery(eu.ohim.sp.core.application.DraftApplication.class);
        Root<eu.ohim.sp.core.application.DraftApplication> entityRoot = criteria.from(eu.ohim.sp.core.application.DraftApplication.class);
        criteria.select(entityRoot);

        try {
            //Sets the order by criteria
            if(sortDirection != null
                    && StringUtils.isNotBlank(sortProperty)) {
                javax.persistence.criteria.Order
                        order = (sortDirection ? builder.asc(entityRoot.get(sortProperty))
                                            : builder.desc(entityRoot.get(sortProperty)));
                criteria.orderBy(order);
            }
			addSearchCriterias(builder, entityRoot, criteria, searchCriteria);

            query = entityManager.createQuery(criteria);
            query.setFirstResult(pageIndex);
            query.setMaxResults(pageSize);
            List<Object> objectList = query.getResultList();
            for (Object object : objectList) {
                draftApplicationList.add(getDraftApplication((eu.ohim.sp.core.application.DraftApplication) object));
            }
        } catch (NoResultException e) {
            logger.debug(e.getMessage());
        } catch (ParseException e) {
			logger.debug("GetDraftApplications parseException: " + e.getMessage());
		}
        return draftApplicationList;
    }
	public long getApplicationsCount(String office, String module, Map<String, String> searchCriteria) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<eu.ohim.sp.core.application.DraftApplication> entityRoot = criteria.from(eu.ohim.sp.core.application.DraftApplication.class);
		criteria.select(builder.count(entityRoot));

		try {

			addSearchCriterias(builder, entityRoot, criteria, searchCriteria);

			return entityManager.createQuery(criteria).getSingleResult().longValue();

		} catch (NoResultException e) {
			logger.debug(e.getMessage());
		} catch (ParseException e) {
			logger.debug("GetDraftApplications parseException: " + e.getMessage());
		}
		return 0;
	}

	private void addSearchCriterias(CriteriaBuilder builder, Root entityRoot, CriteriaQuery criteria, Map<String, String> searchCriteria) throws ParseException {
		if (searchCriteria!=null) {
			List<Predicate> andPredicates = new ArrayList<Predicate>();
			for (Map.Entry<String, String> entryCriteria : searchCriteria.entrySet()) {
				if (StringUtils.isNotBlank(entryCriteria.getKey())) {
					Predicate predicate = null;
					if ("tyApplication".equals(entryCriteria.getKey())) {
						List<TypeApplication> typeApplications = new ArrayList<>();
						StringTokenizer st = new StringTokenizer(entryCriteria.getValue(), ",");
						while (st.hasMoreTokens()) {
							String tyApplicationDescription = st.nextToken().trim();
							TypeApplication typeApplication = getTypeApplicationByDescription(tyApplicationDescription);// Convert to TypeApplication
							if (typeApplication != null) {
								typeApplications.add(typeApplication);
								logger.debug("	>>> GetDraftApplications SearchCriteria: "
										+ "TypeApplication found with id: " + typeApplication.getId()
										+ ", TypeApplication: " + typeApplication.getTypeApplication());
							} else {
								logger.debug("	>>> GetDraftApplications SearchCriteria: "
										+ "TypeApplication not found: " + tyApplicationDescription);
							}
						}
						Expression<TypeApplication> exp = entityRoot.get("tyApplication");
						predicate = exp.in(typeApplications);

					} else if ("currentStatus".equals(entryCriteria.getKey())) {
						// Convert to Status
						List<Status> statusList = new ArrayList<Status>();
						StringTokenizer st = new StringTokenizer(entryCriteria.getValue(), ",");
						while (st.hasMoreTokens()) {
							String description = st.nextToken();
							Status status = getStatusByDescription(description);
							if (status != null) {
								statusList.add(status);
								logger.debug("	>>> GetDraftApplications SearchCriteria: "
										+ "Status found with id: " + status.getCode()
										+ ", Description: " + status.getDescription());
							} else {
								logger.debug("	>>> GetDraftApplications SearchCriteria: "
										+ "Status not found: " + entryCriteria.getValue());
							}
						}
						Expression<Status> exp = entityRoot.get("currentStatus");
						predicate = exp.in(statusList);
					} else if ("currentSignStatus".equals(entryCriteria.getKey())) {
						// Convert to Status
						List<Long> statusList = new ArrayList<Long>();
						StringTokenizer st = new StringTokenizer(entryCriteria.getValue(), ",");
						while (st.hasMoreTokens()) {
							String status = st.nextToken();
							Long l;
							try {
								l = Long.parseLong(status);
							} catch (NumberFormatException e) {
								l = null;
							}
							if (l != null) {
								statusList.add(l);
								logger.debug("	>>> GetDraftApplications SearchCriteria: "
										+ "Sign Status found with id: " + l);
							} else {
								logger.debug("	>>> GetDraftApplications SearchCriteria: "
										+ "Sign Status not found: " + entryCriteria.getValue());
							}
						}
						Join<DraftApplication, DraftSignStatus> currentSignStatusJoin = entityRoot.join("currentSignStatus", JoinType.LEFT);

						Expression<Status> exp = currentSignStatusJoin.get("signStatus");
						predicate = exp.in(statusList);
					} else if ("firstDate".equals(entryCriteria.getKey())) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date firstDate = sdf.parse(entryCriteria.getValue());
						firstDate.setHours(0);
						firstDate.setMinutes(0);
						firstDate.setSeconds(0);
						Expression<Date> exp = entityRoot.get("dtCreated");
						predicate = builder.greaterThanOrEqualTo(exp, firstDate);
						logger.debug("	>>> GetDraftApplications SearchCriteria: FirstDate: " + entryCriteria.getValue());
					} else if ("lastDate".equals(entryCriteria.getKey())) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date lastDate = sdf.parse(entryCriteria.getValue());
						lastDate.setHours(23);
						lastDate.setMinutes(59);
						lastDate.setSeconds(59);
						Expression<Date> exp = entityRoot.get("dtCreated");
						predicate = builder.lessThanOrEqualTo(exp, lastDate);
						logger.debug("	>>> GetDraftApplications SearchCriteria: LastDate: " + entryCriteria.getValue());
					} else if ("firstDateSign".equals(entryCriteria.getKey())) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date firstDate = sdf.parse(entryCriteria.getValue());
						firstDate.setHours(0);
						firstDate.setMinutes(0);
						firstDate.setSeconds(0);
						Join<DraftApplication, DraftSignStatus> currentSignStatusJoin = entityRoot.join("currentSignStatus", JoinType.LEFT);
						Expression<Date> exp = currentSignStatusJoin.get("modifiedDate");
						predicate = builder.greaterThanOrEqualTo(exp, firstDate);
						logger.debug("	>>> GetDraftApplications SearchCriteria: FirstDateSign: " + entryCriteria.getValue());
					} else if ("lastDateSign".equals(entryCriteria.getKey())) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date lastDate = sdf.parse(entryCriteria.getValue());
						lastDate.setHours(23);
						lastDate.setMinutes(59);
						lastDate.setSeconds(59);
						Join<DraftApplication, DraftSignStatus> currentSignStatusJoin = entityRoot.join("currentSignStatus", JoinType.LEFT);
						Expression<Date> exp = currentSignStatusJoin.get("modifiedDate");
						predicate = builder.lessThanOrEqualTo(exp, lastDate);
						logger.debug("	>>> GetDraftApplications SearchCriteria: LastDateSign: " + entryCriteria.getValue());
					} else {
						predicate = builder.like(entityRoot.<String>get(entryCriteria.getKey()), "%" + entryCriteria.getValue() + "%");
						logger.debug("	>>> GetDraftApplications SearchCriteria: Key: " + entryCriteria.getKey() + ", Value: " + entryCriteria.getValue());
					}
					if (predicate != null) {
						andPredicates.add(predicate);
					}
				}
			}

			if (andPredicates != null && !andPredicates.isEmpty()) {
				criteria.where(andPredicates.toArray(new Predicate[andPredicates.size()]));
			}
		}
	}
	public List<String> getTypeApplicationNames() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TypeApplication> query = builder.createQuery(TypeApplication.class);
		Root<TypeApplication> root = query.from(TypeApplication.class);
		query.select(root);

		List<TypeApplication> lst = entityManager.createQuery(query).getResultList();
		return lst.stream().map(l -> l.getTypeApplication()).collect(Collectors.toList());
	}

    public eu.ohim.sp.core.domain.application.DraftApplication getDraftApplication(eu.ohim.sp.core.application.DraftApplication draftApplication) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        logger.debug("		>>> GetDraftApplication START");
        
        List<String> mappingFiles = new ArrayList<String>();
        mappingFiles.add("dozerBeanMapping.xml");
        mapper.setMappingFiles(mappingFiles);
        eu.ohim.sp.core.domain.application.DraftApplication returnedDraftApplication = mapper.map(draftApplication, eu.ohim.sp.core.domain.application.DraftApplication.class);

        if (!returnedDraftApplication.getStatuses().isEmpty()) {
            eu.ohim.sp.core.domain.application.DraftStatus draftStatus = returnedDraftApplication.getStatuses().iterator().next();
            if (returnedDraftApplication.getCurrentStatus() == null) {
                returnedDraftApplication.setCurrentStatus(draftStatus);
            } else if (returnedDraftApplication.getCurrentStatus() != null
                    && (returnedDraftApplication.getCurrentStatus().getModifiedDate()==null ||
                    returnedDraftApplication.getCurrentStatus().getMessage()==null)
                    && (draftStatus.getStatus() != null && returnedDraftApplication.getCurrentStatus().getStatus().equals(draftStatus.getStatus()))) {
                returnedDraftApplication.getCurrentStatus().setMessage(draftStatus.getMessage());
                returnedDraftApplication.getCurrentStatus().setModifiedDate(draftStatus.getModifiedDate());
            }
        }
        
        logger.debug("		>>> GetDraftApplication END");
        return returnedDraftApplication;
    }

}
