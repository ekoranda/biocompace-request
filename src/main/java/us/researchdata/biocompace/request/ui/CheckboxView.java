package us.researchdata.biocompace.request.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

@ManagedBean
public class CheckboxView {
 
    private String[] selectedConsoles;
    private String[] selectedConsoles2;

    private String[] selectedTools;
    private List<String> tools;

 
    @PostConstruct
    public void init() {
        tools = new ArrayList<String>();
        tools.add("AceMD");
        tools.add("AutoDock Tools");
        tools.add("BEAST");
        tools.add("Bowtie2");
        tools.add("BWA");
        tools.add("CEAS");
        tools.add("Chimera");
        tools.add("Cufflinks");
        tools.add("Cytoscape");
        tools.add("DESeq");
        tools.add("edgeR");
        tools.add("EMBOSS");
        tools.add("figtree");
        tools.add("GATK + Queue");
        tools.add("IGV");
        tools.add("Inkscape");
        tools.add("I-TASSER");
        tools.add("MACS");
        tools.add("MEGA");
        tools.add("MEME");
        tools.add("Muscle");
        tools.add("MySQL");
        tools.add("NAMD");
        tools.add("picard");
        tools.add("PyMOL");
        tools.add("R/R Studio");
        tools.add("RepeatMasker");
        tools.add("Samtools");
        tools.add("SnpEff");
        tools.add("SOAPdenovo2");
        tools.add("TopHat");
        tools.add("tracer");
        tools.add("Trinity");
        tools.add("UGENE-NGS");
        tools.add("USeq");
        tools.add("VINA");
        tools.add("VMD");
        tools.add("blast standalone plus blastdb's (db + appl.)");
        tools.add("GATK resource bundle");
        tools.add("iGenomes");
        tools.add("PDB");
        tools.add("Reactome");
        tools.add("Cross_Match/Phred/Phrap/Consed");
        tools.add("APBS");
        tools.add("Blender");
        tools.add("Bowtie");
        tools.add("cummeRbund");
        tools.add("DendroPy");
        tools.add("DEXSeq");
        tools.add("easyRNASeq");
        tools.add("Galaxy");
        tools.add("gap4 and gap5 (Staden package)");
        tools.add("Garli");
        tools.add("GMAP/GSNAP");
        tools.add("HyPhy");
        tools.add("Jalview");
        tools.add("limma");
        tools.add("MIRA");
        tools.add("MrBayes");
        tools.add("Open Babel");
        tools.add("PDB2QR");
        tools.add("PHYLIP");
        tools.add("ProDy");
        tools.add("RSEM");
        tools.add("Ligand Expo");
        tools.add("Zinc");
        tools.add("Toqure MAUI PBS");
        tools.add("MPICH");
        tools.add("OpenMPI");
        tools.add("SPSS");
        tools.add("Anaconda");

    }
 
    public String[] getSelectedConsoles() {
        return selectedConsoles;
    }
 
    public void setSelectedConsoles(String[] selectedConsoles) {
        this.selectedConsoles = selectedConsoles;
    }
 

 

 
    public String[] getSelectedTools() {
        return selectedTools;
    }
 
    public void setSelectedTools(String[] selectedTools) {
        this.selectedTools = selectedTools;
    }
 
    public String[] getSelectedConsoles2() {
        return selectedConsoles2;
    }
 
    public void setSelectedConsoles2(String[] selectedConsoles2) {
        this.selectedConsoles2 = selectedConsoles2;
    }
 
    public List<String> getTools() {
        return tools;
    }
 


 
}