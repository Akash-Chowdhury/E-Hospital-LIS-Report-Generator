package com.hl72pdf.controller;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v23.message.ORU_R01;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.util.Terser;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Controller

public class OruMessageController {

    @PostMapping(value = "/generate-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    
    public ResponseEntity<byte[]> generatePdf(@RequestBody String oruMessage) throws IOException {
    	
        try {
        	
            // Create the HAPI context
        	
        	HapiContext context = new DefaultHapiContext();
        	
            // Parse the ORU message
        	
        	Parser parser = context.getPipeParser();
            Message message = parser.parse(oruMessage);
            
            
            
            ORU_R01 msg;
            
            
            try {
                msg = (ORU_R01) parser.parse(oruMessage);
            }
            catch (Exception e) 
            {
                e.printStackTrace();
                
                return null ;
            }
            
            
            /* 
              		 * Setting the following property allows you to specify a default
               		 * value to assume if OBX-2 is missing. 
           */
             
    		context.getParserConfiguration().setDefaultObx2Type("ST");
    		  
    		// Parsing now succeeds
    		
    		msg = (ORU_R01) parser.parse(oruMessage);
    		System.out.println("Message is valid");

            // Extract and print specific segments and fields using Terser
    		
            Terser terser = new Terser(msg);
            
            
            /* Message Header Segment */
            
            String Send_App = terser.get("/.MSH-3");
            
            System.out.println("Sending Application :" + Send_App);
            
            
            /* Paitent Identification */
            
            
            String SeqNo = terser.get("/.PID-1");
            String PaitentID = terser.get("/.PID-2");
            String FirstName = terser.get("/.PID-5-1");
            String LastName = terser.get("/.PID-5-2");
            String DOB = terser.get("/.PID-7");
            String Address = terser.get("/.PID-11-1")+","+terser.get("/.PID-11-2");
            String Sex =  terser.get("/.PID-8");
            String PhoneNo= terser.get("/.PID-13");
            String PaitentAccountNumber = terser.get("/.PID-18");
            String Race = terser.get("/.PID-10");
            
            // OBR segment
            String desc = terser.get("/.OBR-4-2");
            String c_time = terser.get("/.OBR-7");
            c_time = c_time.substring(8, 10) + ":" + c_time.substring(10, 12) + ", " + c_time.substring(6,8) + "/" + c_time.substring(4,6)+ "/" + c_time.substring(0,4);
            String r_time =  terser.get("/.OBR-14");
            r_time = r_time.substring(8, 10) + ":" + r_time.substring(10, 12) + ", " + r_time.substring(6,8) + "/" + r_time.substring(4,6)+ "/" + r_time.substring(0,4);
            String labid = terser.get("/.OBR-2");
            String hospitalid = terser.get("/.OBR-3");
            
            // OBS segment WBC
            
            int cnt = 0;
            
            String key = "/.OBSERVATION(" + cnt +")";
            
            String composition_description = terser.get(key + "/OBX-3-2");
            String observation_val = terser.get(key + "/OBX-5");
            String observation_unit = terser.get(key + "/OBX-6");
            String reference_range = terser.get(key + "/OBX-7"); 
            String Conclusion = terser.get(key + "/OBX-8");
           // String obs_res_stats = terser.get(key + "/OBX-11");
            
            // OBS segment  Hemoglobin
            
            int cntHemo = 1;
            
            String key1 = "/.OBSERVATION(" + cntHemo +")";
            
            String composition_description1 = terser.get(key1 + "/OBX-3-2");
            String observation_val1 = terser.get(key1 + "/OBX-5");
            String observation_unit1 = terser.get(key1 + "/OBX-6");
            String reference_range1 = terser.get(key1 + "/OBX-7"); 
            String Conclusion1 = terser.get(key1 + "/OBX-8");
            
            int cntHemo2 = 2;
            
            String key2 = "/.OBSERVATION(" + cntHemo2 +")";
            
            String composition_description2 = terser.get(key2 + "/OBX-3-2");
            String observation_val2 = terser.get(key2 + "/OBX-5");
            String observation_unit2 = terser.get(key2 + "/OBX-6");
            String reference_range2 = terser.get(key2 + "/OBX-7"); 
            String Conclusion2 = terser.get(key2 + "/OBX-8");


            

            // Generate the PDF document
            
            
            Document document = new Document(PageSize.A4);
            File file = new File("D:/Downloads/result.pdf");
            FileOutputStream outputStream = new FileOutputStream(file);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            
            // TITLE 
    		Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
    		fontTitle.setSize(20);
    		Font fontTitle_1 = FontFactory.getFont(FontFactory.TIMES_BOLD);
    		fontTitle_1.setSize(10);
    		Font font_low = FontFactory.getFont(FontFactory.TIMES_BOLD);
    		font_low.setSize(10);
    		Paragraph pgrp1 = new Paragraph("Agartala Government Medical College Hospital", fontTitle);
    		pgrp1.setAlignment(Paragraph.ALIGN_CENTER);
    		
    		Paragraph pgrp2 = new Paragraph("Government of Tripura, Agartala, Tripura West ,799006", fontTitle_1);
    		pgrp2.setAlignment(pgrp2.ALIGN_CENTER);
    		
    		Paragraph pgrp3 = new Paragraph("LABORATORY OBSERVATION REPORT", fontTitle_1);
    		pgrp3.setAlignment(pgrp3.ALIGN_CENTER);
    		
            Font font = FontFactory.getFont("Times New Roman", BaseFont.IDENTITY_H, true, 12, Font.NORMAL, BaseColor.BLACK);
            document.add(pgrp1);
            document.add(pgrp2);
            document.add(pgrp3);
            document.add(new Paragraph("____________________________________________________________________________"));
            
            document.add(new Paragraph("Paitent ID : " + PaitentID, font));
            document.add(new Paragraph("Patient Name: " + FirstName.toUpperCase() + " " + LastName.toUpperCase(), font));
            document.add(new Paragraph("DOB : " + DOB.substring(6,8) + "/" + DOB.substring(4,6)+ "/" + DOB.substring(0,4), font));
            document.add(new Paragraph("Gender : " + Sex, font));
            document.add(new Paragraph("Address: " + Address, font));
            document.add(new Paragraph("Contact: " + PhoneNo, font));
            document.add(new Paragraph("_____________________________________________________________________________ " , font));
            
            document.add(new Paragraph("Description: " + desc, font));
            document.add(new Paragraph("Hospital Id : " + hospitalid, font));
            document.add(new Paragraph("Lab Id : " + labid, font));
            document.add(new Paragraph("Collect Time : " +  c_time, font));
            document.add(new Paragraph("Result Time : " + r_time, font));
           
            
            document.add(new Paragraph("____________________________________________________________________________"));
            
            document.add(new Paragraph("Composition_Description: " + composition_description, font));
            document.add(new Paragraph("Observation_Value : " + observation_val, font));
            document.add(new Paragraph("Observation_Unit : " + observation_unit, font));
            document.add(new Paragraph("Reference_Range : " + reference_range, font));
            document.add(new Paragraph("Conclusion : " + Conclusion, font));
            
            document.add(new Paragraph("____________________________________________________________________________"));
            
            document.add(new Paragraph("Composition_Description: " + composition_description1, font));
            document.add(new Paragraph("Observation_Value : " + observation_val1, font));
            document.add(new Paragraph("Observation_Unit : " + observation_unit1, font));
            document.add(new Paragraph("Reference_Range : " + reference_range1, font));
            document.add(new Paragraph("Conclusion : " + Conclusion1, font));
            
            document.add(new Paragraph("____________________________________________________________________________"));
            
            document.add(new Paragraph("Composition_Description: " + composition_description2, font));
            document.add(new Paragraph("Observation_Value : " + observation_val2, font));
            document.add(new Paragraph("Observation_Unit : " + observation_unit2, font));
            document.add(new Paragraph("Reference_Range : " + reference_range2, font));
            document.add(new Paragraph("Conclusion : " + Conclusion2, font));
            document.add(new Paragraph("____________________________________________________________________________"));
            
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("#This is computer generated report, here no signarure required. It is verified by Hospital admin", font_low));
 
            document.close();

            
            // Set the response headers
            
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "result.pdf");

            // Return the PDF file as a byte array
            
            byte[] pdfBytes = Files.readAllBytes(file.toPath());
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
            } 
        
        catch (IOException | DocumentException | HL7Exception e) {
            //e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
