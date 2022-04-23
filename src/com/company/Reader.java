package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.json.simple.parser.ParseException;

public class Reader {

    Parser pars = new Parser();
    ArrayList<Reactor> Re = new ArrayList<>();

    public ArrayList<Reactor> Read (File f) throws FileNotFoundException, ParseException {
        if (f.getPath().endsWith(".yml")) {
            Re = pars.YAML(f.getName());
        }
        if (f.getPath().endsWith(".xml")) {
            Re = pars.XML(f.getPath());
        }
        if (f.getPath().endsWith(".json")) {
            try {
                Re = pars.JSON(f.getPath());
            } catch (IOException ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Re;
    }

    public void Tree(JTree t, ArrayList<Reactor> reactors){
        DefaultTreeModel tr = (DefaultTreeModel)t.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)t.getSelectionPath().getLastPathComponent();
        DefaultMutableTreeNode sourse = new DefaultMutableTreeNode(reactors.get(0).getSource());
        root.add(sourse);
        for (int i=0;i<15;i++){
            DefaultMutableTreeNode y = new DefaultMutableTreeNode(reactors.get(i).getName());
            sourse.add(y);
            String str[] = {"Class: "+reactors.get(i).getName(),
                    "Burnup: "+Double.toString(reactors.get(i).getBurnup()),
                    "Kpd: "+Double.toString(reactors.get(i).getKpd()),
                    "Enrichment: "+Double.toString(reactors.get(i).getEnrichment()),
                    "Termal capacity: "+Double.toString(reactors.get(i).getTermal_capacity()),
                    "Electrical capacity: "+Double.toString(reactors.get(i).getElectrical_capacity()),
                    "Life time: "+Double.toString(reactors.get(i).getLife_time()),
                    "First load: "+Double.toString(reactors.get(i).getFirst_load())};
            for (int j=0;j<8;j++){
                y.add(new DefaultMutableTreeNode(str[j]));
            }
        }
        tr.reload();}}




