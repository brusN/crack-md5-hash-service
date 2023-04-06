package ru.nsu.brusn.lab1.model.dto.response;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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