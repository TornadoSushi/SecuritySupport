package com.infinity.jerry.securitysupport.common.z_utils.z_tools;

import android.util.Log;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 */
public class WordUtils {

    public String writeDoc(File templateFile, File newFile, Map<String, String> map) {
        String name = "";
        try {
            HWPFDocument document = new HWPFDocument(new FileInputStream(templateFile));
            Range range = document.getRange();
            Set<String> keys = map.keySet();
            for (String key : keys) {
                range.replaceText(key, map.get(key));
            }
            FileOutputStream out = new FileOutputStream(newFile, false);
            document.write(out);
            out.close();
            name = newFile.getAbsolutePath();
        } catch (Exception e) {
            Log.e("WordUtils", e.toString());
        }
        return name;
    }

    public void writeDoc(String templateFile, String newFile, Map<String, String> map) {
        try {
            HWPFDocument document = new HWPFDocument(new FileInputStream(templateFile));
            Range range = document.getRange();
            Set<String> keys = map.keySet();
            for (String key : keys) {
                range.replaceText(key, map.get(key));
            }
            FileOutputStream out = new FileOutputStream(newFile, false);
            document.write(out);
            out.close();
        } catch (Exception e) {
            Log.e("WordUtils", e.toString());
        }
    }

    public void writeDoc(InputStream templateFile, String newFile, Map<String, String> map) {
        try {
            HWPFDocument document = new HWPFDocument(templateFile);
            Range range = document.getRange();
            Set<String> keys = map.keySet();
            for (String key : keys) {
                range.replaceText(key, map.get(key));
            }
            FileOutputStream out = new FileOutputStream(newFile, false);
            document.write(out);
            out.close();
        } catch (Exception e) {
            Log.e("WordUtils", e.toString());
        }
    }

}
