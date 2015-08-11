/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.services.model;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author iulian.radulescu
 */
public enum PopulationAttribute {
     COD( new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.AUTO, 30L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.ACCOMODATION, 33L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.LIBRARIES, 37L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.MUSEUMS, 41L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.SHOWCOMPANIES, 45L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.MEDICALUNITS, 49L),
             new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.SCHOOLS, 53L)
     ),
     
     DENI(new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.AUTO, 31L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.ACCOMODATION, 34L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.LIBRARIES, 38L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.MUSEUMS, 42L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.SHOWCOMPANIES, 46L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.MEDICALUNITS, 50L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.SCHOOLS, 54L)
     ),
     
    JUD(new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.AUTO, 32L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.ACCOMODATION, 35L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.LIBRARIES, 39L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.MUSEUMS, 43L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.SHOWCOMPANIES, 47L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.MEDICALUNITS, 51L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.SCHOOLS, 55L)
     ),
    
    SIRUTA(new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.AUTO, 0L ),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.ENTERPRISE, 36L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.LIBRARIES, 40L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.MUSEUMS, 44L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.SHOWCOMPANIES, 48L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.MEDICALUNITS, 52L),
                new AbstractMap.SimpleImmutableEntry<SurveyPopulation, Long>(SurveyPopulation.SCHOOLS, 56L)
     );
    
    private Map<SurveyPopulation,Long> attributeIDs = new HashMap<>( ); 
    
    PopulationAttribute( AbstractMap.SimpleImmutableEntry... entries ) {
        if ( entries !=null ) {
            for ( AbstractMap.SimpleImmutableEntry entry : entries ) {
                attributeIDs.put( (SurveyPopulation)entry.getKey(), (Long)entry.getValue() );
            }
        }
    }
    
    public Long idForPopulation( SurveyPopulation population ) {
        Long id = attributeIDs.get( population );
        return id == 0L ? null: id;
    }
}
