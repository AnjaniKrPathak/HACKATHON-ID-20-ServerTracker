package com.example.servertracker.report;

import com.example.servertracker.server.entity.ServerDashbordDetail;
import com.example.servertracker.server.service.IServerService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    IServerService serverService;
    @GetMapping("/dashbordReport")
    public ResponseEntity<byte[]>  dashbordSpaceReport(@RequestParam Long userId){
        List<ServerDashbordDetail> serverDashbordDetails =new ArrayList<>();
        try{
            serverDashbordDetails=serverService.getServerDashbordDetail(userId);
            System.out.println("Dashbord Detail size"+serverDashbordDetails.size());
            //tableSpaceList.add(new ServerTableSpace("NC_DATA",49152,44855.6875,4296.3125,91));
            // tableSpaceList.add(new ServerTableSpace("NC_INDEXES",12288,8090.375,4197.625,65));
            Map<String,Object> dashbordReport=new HashMap<>();
            dashbordReport.put("CompanyName","Netcracker Technology");
            dashbordReport.put("DashbordDetailData",new JRBeanCollectionDataSource(serverDashbordDetails));
            JasperPrint empReport =
                    JasperFillManager.fillReport
                            (
                                    JasperCompileManager.compileReport(
                                            ResourceUtils.getFile("classpath:dashbord-details.jrxml")
                                                    .getAbsolutePath()) // path of the jasper report
                                    , dashbordReport // dynamic parameters
                                    , new JREmptyDataSource()
                            );

            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "dashborddetail.pdf");
            //create the report in PDF format
            return new ResponseEntity<byte[]>
                    (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
