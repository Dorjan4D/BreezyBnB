package com.breezybnb.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ReminderDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        String resId = (String) execution.getVariable("accommodation");
        String host  = execution.getCurrentActivityName();
        System.out.println("[REMINDER] One minute passed – still waiting for host. "
                + "Process=" + execution.getProcessInstanceId()
                + " accommodation=" + resId + " activity=" + host);
    }
}
