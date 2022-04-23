package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;

public class Parser {
    public ArrayList<Reactor> JSON(String s) throws FileNotFoundException, IOException, ParseException {
        ArrayList<Reactor> reactorsList = new ArrayList<>();

        FileReader reader = new FileReader(s);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);


        for (Object obj : jsonArray) {
            JSONObject pars = (JSONObject) obj;
            reactorsList.add(new Reactor((String) pars.get("class"), (Double) pars.get("burnup"), (Double) pars.get("kpd"),
                    (Double) pars.get("enrichment"), (Double) pars.get("termal_capacity"), (Double) pars.get("electrical_capacity"),
                    (Double) pars.get("life_time"), (Double) pars.get("first_load"),"JSON"));
        }

        return reactorsList;
    }

    public ArrayList<Reactor> XML(String s) {
        ArrayList<Reactor> reactorsList = new ArrayList();
        Reactor reactor = null;
        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(s));

            while (reader.hasNext()) {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement start = xmlEvent.asStartElement();
                    if (start.getName().getLocalPart().equals("TYPE")) {
                        reactor = new Reactor();
                    }
                    if (start.getName().getLocalPart().equals("class")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setName(xmlEvent.asCharacters().getData());
                    } else if (start.getName().getLocalPart().equals("burnup")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setBurnup(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (start.getName().getLocalPart().equals("kpd")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setKpd(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (start.getName().getLocalPart().equals("enrichment")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setEnrichment(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (start.getName().getLocalPart().equals("termal_capacity")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setTermal_capacity(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (start.getName().getLocalPart().equals("electrical_capacity")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setElectrical_capacity(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (start.getName().getLocalPart().equals("life_time")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setLife_time(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (start.getName().getLocalPart().equals("first_load")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setFirst_load(Double.parseDouble(xmlEvent.asCharacters().getData()));
                        reactor.setSource("XML");
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("TYPE")) {
                        reactorsList.add(reactor);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }

        return reactorsList;
    }

    public ArrayList<Reactor> YAML(String  s) throws FileNotFoundException {

        ArrayList<Reactor> reactorsArrayList = new ArrayList<>();
        Reactor r = new Reactor();

        InputStream inputStream = new FileInputStream(new File(s));
        Yaml yaml = new Yaml(new Constructor(ReactorType.class));
        ReactorType data = (ReactorType) yaml.load(inputStream);
        reactorsArrayList.addAll(data.getReactorType());

        return reactorsArrayList;
    }
}
