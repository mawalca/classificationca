package pl.edu.ug.caclassification.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.edu.ug.caclassification.simulation.SimResult;

public class ResultFilesWriter {

	private String resultDirPath;
	
	public ResultFilesWriter() {
		resultDirPath = getResultDirPath();
		(new File(resultDirPath)).mkdirs();
	}
	
	public void saveErrors(List<SimResult> simResults, int actual) {
		File dirError = new File(resultDirPath);
		addErrorHeader(simResults, dirError);
		
		saveErrorToFile(simResults, dirError, actual);
	}
	
	private void addErrorHeader(List<SimResult> simResults, File dirError) {
		Path path = Paths.get(dirError.getAbsolutePath() + "/" + simResults.get(0).getImageName() + "Error.csv");
		try {		
			BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			
			StringBuilder header = new StringBuilder();
			header.append("#");
			simResults.forEach(simResult -> {
				header.append(",");
				header.append(simResult.getRuleName());
				if (simResult.getDiscretImg() != null) {
					header.append(", ");
				}
			});
			writer.write(header.toString()); 
            writer.newLine();
            writer.flush();
            writer.close();
		} catch (IOException e) { 
            System.err.format("IOException: %s%n", e);
		}
	}

	public void saveToFiles(SimResult simResult, int actual) {
		
		File imageDirPath = getImageDirPathAndSaveOrigImage(simResult);
		
		File simDirPath = getSimDirPathAndSaveStartImage(simResult, imageDirPath + "/" + actual);
		
		String ruleName = simResult.getRule().toString();

		File ruleDir = new File(simDirPath + "/" + ruleName);
		ruleDir.mkdirs();
		
		Path finalImg = Paths.get(ruleDir.getAbsolutePath() + "/finalImage.csv");
		saveImg(finalImg, simResult.getFinalImg());

		File dirMidIters = new File(ruleDir.getAbsolutePath() + "/midIters");
		dirMidIters.mkdirs();
		int midItersAmount = simResult.getMidIterImgs().size();
		
		for (int j = 0; j < midItersAmount; j++) {
			Path midIterImgPath = Paths.get(dirMidIters.getAbsolutePath() + "/midIter" + (j+1) + "Image.csv");
			saveImg(midIterImgPath, simResult.getMidIterImgs().get(j));
		}
		
		if (simResult.getSamples() != null) {
			File dirSamples = new File(ruleDir.getAbsolutePath() + "/samples");
			dirSamples.mkdirs();
			int samplesAmount = simResult.getSamples().size();
			for (int j = 0; j < samplesAmount; j++) {
				Path samplePath = Paths.get(dirSamples.getAbsolutePath() + "/sample" + (j+1) + "Image.csv");
				saveImg(samplePath, simResult.getSamples().get(j));
			}
		}
		
		if (simResult.getDiscretImg() != null) {
			Path discretImg = Paths.get(ruleDir.getAbsolutePath() + "/discretImage.csv");
			saveImg(discretImg, simResult.getDiscretImg());
		}
	}
	
	private void saveErrorToFile(List<SimResult> simResults, File ruleDir, int actual) {
		
		Path path = Paths.get(ruleDir.getAbsolutePath() + "/" + simResults.get(0).getImageName() + "Error.csv");
		try {		
			BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			
			StringBuilder rSb = new StringBuilder();
            rSb.append(actual);            
			
			simResults.forEach(simResult -> {
				
				rSb.append(",");
	            float error = Utils.imgError(simResult.getOriginalImage(), simResult.getFinalImg());
	            rSb.append(error);
	            
	            if (simResult.getDiscretImg() != null) {
	            	rSb.append(",");
	            	error = Utils.imgError(simResult.getOriginalImage(), simResult.getDiscretImg());
	            	rSb.append(error);
	            }
			});
			writer.write(rSb.toString());
            writer.newLine();
            
            writer.flush();
            writer.close();
            
		} catch (IOException e) { 
            System.err.format("IOException: %s%n", e);
		}
	}

	private File getSimDirPathAndSaveStartImage(SimResult simResult, String path) {
		File simDirPath = new File(path);
		simDirPath.mkdirs();
		
		Path imgPath = Paths.get(simDirPath + "/startImage.csv");
		saveImg(imgPath, simResult.getStartImg());
		
		return simDirPath;
	}

	private String getResultDirPath() {
		String dateName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
		return "./results/" + dateName;
	}
	
	private File getImageDirPathAndSaveOrigImage(SimResult simResult) {
		
		File simDirPath = new File(resultDirPath + "/" + simResult.getImageName());
		simDirPath.mkdirs();
		
		Path origImgPath = Paths.get(simDirPath + "/originalImage.csv");
		saveImg(origImgPath, simResult.getOriginalImage());
		
		return simDirPath;
	}

	private void saveImg(Path path, float[][] img) {

		try {
			BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);

			for (float[] line : img) {
				StringBuilder textLine = new StringBuilder();
				for (int i = 0; i < line.length; i++) {
					textLine.append(line[i]);
					if (i < line.length - 1) {
						textLine.append(",");
					}
				}
				writer.write(textLine.toString());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
