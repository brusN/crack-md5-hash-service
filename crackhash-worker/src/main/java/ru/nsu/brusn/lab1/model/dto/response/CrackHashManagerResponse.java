package ru.nsu.brusn.lab1.model.dto.response;

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
public class CrackHashManagerResponse {
    @XmlElement(name = "RequestId")
    private String requestId;
    @XmlElement(name = "Data")
    private String data;
}
