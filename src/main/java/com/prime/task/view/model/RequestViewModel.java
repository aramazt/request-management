package com.prime.task.view.model;

import com.prime.task.utils.RequestStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class RequestViewModel {

    public Long id;

    @NotNull
    @NotEmpty
    @Size(max = 20)
    public String name;

    @NotNull
    @Size(max = 500)
    public String description;

    @NotNull
    public RequestStatus status;

    public Long responsibleId;

    public String prevResponsible;
}
