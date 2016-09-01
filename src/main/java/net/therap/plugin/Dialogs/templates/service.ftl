package ${PACKAGE_NAME};


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import net.therap.db.util.Action;
import net.therap.db.util.CommonForm;
import net.therap.core.ejb.service.ArchiverService;
import net.therap.core.ejb.service.EventPublisher;
import net.therap.security.UserContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

<#if imports?size != 0 >
    <#list imports as import>
    import ${import};
    </#list>
</#if>

@Repository
public class ${CLASS_NAME}Service {

    private static final Logger log = LoggerFactory.getLogger(${CLASS_NAME}Service.class);

    @PersistenceContext(unitName = "emf")
    private EntityManager em;

    @Autowired
    private ArchiverService archiverService;

    @Autowired
    private EventPublisher eventPublisher;

    <#--${FIELDS}

    ${METHODS}-->

    private ${CLASS_NAME} doSave(${CLASS_NAME} ${CLASS_INSTANCE_NAME}, Action action, String comments) {

        <#if WORKFLOW_FORM_TYPE??>
            ${CLASS_INSTANCE_NAME}.setStatus(WorkflowManager.getNextStatus(FormType.${WORKFLOW_FORM_TYPE}, ${CLASS_INSTANCE_NAME}.getStatus(), action));
        </#if>

        if (${CLASS_INSTANCE_NAME}.getId() > 0) {
            ${CLASS_INSTANCE_NAME}.setUpdatedBy(UserContext.getLogin());
            ${CLASS_INSTANCE_NAME}.setUpdated(new Date());
            ${CLASS_INSTANCE_NAME} = em.merge(${CLASS_INSTANCE_NAME});
        } else {
            em.persist(${CLASS_INSTANCE_NAME});
        }

        em.flush();

        archiverService.archive(${CLASS_INSTANCE_NAME}, UserContext.getLogin(), ${CLASS_INSTANCE_NAME}.getTz());

        eventPublisher.publish(
            CommonForm.${FT_FORM_TYPE},
            ${CLASS_INSTANCE_NAME}.getFormId(),
            action.name(),
            ${CLASS_INSTANCE_NAME}.getClient().getId(),
            null,
            ${CLASS_INSTANCE_NAME}.getProvider().getId(),
            null,
            null,
            comments,
            null
        );

        return ${CLASS_INSTANCE_NAME};
    }
}
