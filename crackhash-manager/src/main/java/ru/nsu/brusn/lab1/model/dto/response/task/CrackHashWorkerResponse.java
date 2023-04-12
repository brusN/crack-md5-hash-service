package ru.nsu.brusn.lab1.model.dto.response.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "requestId",
        "data",
})
@XmlRootElement(name = "CrackHashManagerRequest")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CrackHashWorkerResponse {
    @XmlElement(name = "RequestId")
    private String requestId;
    @XmlElement(name = "Data")
    private String data;
}