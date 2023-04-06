package ru.nsu.brusn.lab1.mapper;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Component;
import ru.nsu.brusn.lab1.exception.mapper.ObjectMapException;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

import java.io.StringWriter;

@Component
public class CrackHashManagerRequestToXmlMapper implements IMapper<CrackHashManagerRequest, String> {
    @Override
    public String map(CrackHashManagerRequest obj) throws ObjectMapException {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(CrackHashManagerRequest.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(obj, writer);
        } catch (JAXBException e) {
            throw new ObjectMapException(e.getMessage());
        }
        return writer.toString();
    }
}
