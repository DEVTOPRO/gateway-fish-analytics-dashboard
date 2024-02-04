package com.fishmonitor.dashbordtool.utilitys;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInfo {
	
	public List<?> getFilePath(String sourcePath ,String refPath) {
		List<Map<String,Object>> videoList = new ArrayList<Map<String,Object>>();
		try {
			HashMap<String,Object> videoInfo= new HashMap<String, Object>();
			Path videopath = Paths.get(sourcePath);
			if (Files.notExists(videopath) || !Files.isDirectory(videopath)) {
				System.out.println("File are not exist");
			}
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(videopath,"*.{mp4,avi,flv,mov,wmv}");
				for (Path filePath : directoryStream) {
					videoInfo.put("name",filePath.getFileName().toString());
					videoInfo.put("url", refPath+"/"+filePath.getFileName().toString());
					videoList.add(videoInfo);
				}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return videoList;
	}
}
