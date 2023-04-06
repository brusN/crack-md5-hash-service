package ru.nsu.brusn.lab1.mapper;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import ru.nsu.brusn.lab1.exception.mapper.ObjectMapException;
import ru.nsu.brusn.lab1.model.dto.response.task.CrackHashWorkerResponse;

import java.io.StringReader;

public class XmlToCrackHashWorkerResponseMapper implements IMapper<String, CrackHashWorkerResponse> {
    @Override
    public CrackHashWorkerResponse map(String obj) throws ObjectMapException {
        try {
            JAXBContext context = JAXBContext.newInstance(CrackHashWorkerResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return  (CrackHashWorkerResponse) unmarshaller.unmarshal(new StringReader(obj));
        } catch (JAXBException e) {
            throw new ObjectMapException(e.getMessage());
        }
    }
}
