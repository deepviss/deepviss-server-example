package org.deepviss.deepvissserver.mocking;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.deepviss.deepvissserver.model.*;

import java.io.IOException;
import java.util.*;

public class FrameMocker {

    private byte[] imageBytes;

    public FrameMocker() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        imageBytes =IOUtils.toByteArray(classLoader.getResourceAsStream("static/logo-deepviss-96x96.png"));

    }

    public DeepVISSProcessingRequest getMockedProcessingRequest(byte[] contentBytes, String contentType)
    {
        DeepVISSImage deepVISSImage=new DeepVISSImage();
        List<DeepVISSBoundingRectangle> boundingBoxes=new ArrayList<>();
        DeepVISSProcessingRequest deepVISSProcessingRequest=new DeepVISSProcessingRequest();
        deepVISSProcessingRequest.setImage(deepVISSImage);
        deepVISSProcessingRequest.setRegionsOfInterest(boundingBoxes);
        DeepVISSBoundingRectangle deepVISSBoundingRectangle= new DeepVISSBoundingRectangle();
        deepVISSBoundingRectangle.setHeight(100);
        deepVISSBoundingRectangle.setWidth(250);
        deepVISSBoundingRectangle.setLeft(130);
        deepVISSBoundingRectangle.setTop(280);
        deepVISSProcessingRequest.getRegionsOfInterest().add(deepVISSBoundingRectangle);
        deepVISSProcessingRequest.getRegionsOfInterest().add(deepVISSBoundingRectangle);
        deepVISSImage.setImageContentType(contentType);
        deepVISSImage.setImageBase64(contentBytes);

        return deepVISSProcessingRequest;
    }

    public  List<DeepVISSFrame> getMockedFrames() throws IOException {


        Random random = new Random(733183359988335519L);
        int maxFrameIndex = 3;
        int minEventNumber = 2;
        int maxEventNumber = 4;
        boolean hasProcessing = false;
        String sourceId = "HikVision 17 - Entrance";
        int maxFeatureIndex = 128;
        int numberOfKeypoints = 68;
        String[] algorithmsDetection = new String[]{"hog", "faster-rcnn", "yolo"};
        String[] objectTypes = new String[]{"face", "car", "pedestrain", "anomaly"};
        String[] keypointSegmentNames = new String[]{"nose", "mouth", "leftEye", "rightEye"};


        Map<String, Integer> keypointCountPerSegment = new HashMap<>();
        keypointCountPerSegment.put(keypointSegmentNames[0], 4);
        keypointCountPerSegment.put(keypointSegmentNames[1], 16);
        keypointCountPerSegment.put(keypointSegmentNames[2], 14);
        keypointCountPerSegment.put(keypointSegmentNames[3], 14);

        List<DeepVISSFrame> frameList = new ArrayList<>();
        for (int frameIndex = 0; frameIndex < maxFrameIndex; frameIndex++) {
            DeepVISSFrame frame = new DeepVISSFrame();
            frameList.add(frame);
            DeepVISSFrameTimestamp timestamp = new DeepVISSFrameTimestamp();
            timestamp.setReference(DeepVISSFrameTimestamp.ReferenceEnum.ACQUISITION);
            String timestampString = "2018-06-24T23:10:28+03:00";
            timestamp.setValue("2018-06-24T23:10:28+03:00");
            frame.setTimestamps(new ArrayList<>());
            frame.getTimestamps().add(timestamp);
//            frame.setTimestamps(new HashMap<>());
//            frame.getTimestamps().put("reception", timestampString);
            frame.setSourceId(sourceId);
            DeepVISSImage image = new DeepVISSImage();
            image.setImageURL("https://scontent.fotp3-2.fna.fbcdn.net/v/t1.0-9/35671578_587625364954981_8949170630209568768_n.png?_nc_cat=0&oh=82807829ab09846f4a2898d50cbb6cba&oe=5BEC7C21");
            image.setImageBase64(imageBytes);
            image.setImageContentType("image/jpeg");
            frame.setImage(image);
            frame.setEvents(new ArrayList<>());
            int eventNumber = (int) Math.round(minEventNumber + random.nextDouble() * maxEventNumber);
            for (int eventIndex = 0; eventIndex < eventNumber; eventIndex++) {
                DeepVISSEvent event = new DeepVISSEvent();
                if (!hasProcessing) {
                    image = new DeepVISSImage();
                    image.setImageBase64(imageBytes);
                    image.setImageContentType("image/jpeg");
                    event.setProcessedImage(image);
                    hasProcessing = true;
                }
                DeepVISSDetection detection = new DeepVISSDetection();

                detection.setBoundingRectangle(new DeepVISSBoundingRectangle());
                detection.getBoundingRectangle().setHeight(100 + random.nextInt(150));
                detection.getBoundingRectangle().setWidth(100 + random.nextInt(150));
                detection.getBoundingRectangle().setTop(100 + random.nextInt(150));
                detection.getBoundingRectangle().setLeft(100 + random.nextInt(150));

                detection.setOrientation(new DeepVISSOrientation());
                detection.getOrientation().setPitch(random.nextDouble() * 360 - 180);
                detection.getOrientation().setYaw(random.nextDouble() * 360 - 180);
                detection.getOrientation().setRoll(random.nextDouble() * 360 - 180);

                detection.setConfidence(0.8 + random.nextDouble() * 0.2);

//                detection.setKeyPoints(new HashMap<>());
//                for (int segmentIndex = 0; segmentIndex < keypointSegmentNames.length; segmentIndex++) {
//                    int numberOfKeypointsPerSegment = keypointCountPerSegment.get(keypointSegmentNames[segmentIndex]);
//                    detection.getKeyPoints().put(keypointSegmentNames[segmentIndex], new ArrayList<>());
//                    for (int keypointIndex = 0; keypointIndex < numberOfKeypointsPerSegment; keypointIndex++) {
//                        DeepVISSPoint2D point2D = new DeepVISSPoint2D();
//                        point2D.setX(detection.getBoundingRectangle().getLeft() + random.nextInt(detection.getBoundingRectangle().getWidth()));
//                        point2D.setY(detection.getBoundingRectangle().getTop() + random.nextInt(detection.getBoundingRectangle().getHeight()));
//
//                        detection.getKeyPoints().get(keypointSegmentNames[segmentIndex]).add(point2D);
//                    }
//                }

                detection.setKeypoints(new ArrayList<>());
                for (int keypointIndex = 0; keypointIndex < numberOfKeypoints; keypointIndex++) {
                    DeepVISSPoint2D point2D = new DeepVISSPoint2D();
                    point2D.setX(detection.getBoundingRectangle().getLeft() + random.nextInt(detection.getBoundingRectangle().getWidth()));
                    point2D.setY(detection.getBoundingRectangle().getTop() + random.nextInt(detection.getBoundingRectangle().getHeight()));

                    detection.getKeypoints().add(point2D);
                }

                event.setDetection(detection);

                frame.getEvents().add(event);
                event.setAttributes(new ArrayList<>());
                DeepVISSAttribute attribute;

                attribute = new DeepVISSAttribute();
                attribute.setConfidence(0.8 + random.nextDouble() * 0.2);
                attribute.setName("gender");
                attribute.setValue("female");
                attribute.setType(DeepVISSAttribute.TypeEnum.STRING);

                event.getAttributes().add(attribute);

                attribute = new DeepVISSAttribute();
                attribute.setConfidence(0.8 + random.nextDouble() * 0.2);
                attribute.setName("ageGroup");
                attribute.setValue("32-45");
                attribute.setType(DeepVISSAttribute.TypeEnum.STRING);

                event.getAttributes().add(attribute);

                attribute = new DeepVISSAttribute();
                attribute.setConfidence(0.8 + random.nextDouble() * 0.2);
                attribute.setName("age");
                attribute.setValue("38");
                attribute.setType(DeepVISSAttribute.TypeEnum.INTEGER);

                event.getAttributes().add(attribute);

                attribute = new DeepVISSAttribute();
                attribute.setConfidence(0.8 + random.nextDouble() * 0.2);
                attribute.setName("gender");
                attribute.setValue("female");
                attribute.setType(DeepVISSAttribute.TypeEnum.STRING);

                event.getAttributes().add(attribute);

                event.setFeatures(new DeepVISSFeatures());
                event.getFeatures().setAlgorithm("ArcFace-v1.2");
                event.getFeatures().setMetric( DeepVISSMetric.EUCLIDEAN);
                event.getFeatures().setThreshold(0.8);
                event.getFeatures().setVector(new ArrayList<>());
                for (int featureIndex = 0; featureIndex < maxFeatureIndex; featureIndex++) {
                    event.getFeatures().addVectorItem((random.nextDouble() - 0.5) * 2.0);
                }

                event.setId(computeEventId(event, frame));
                event.setObjectType(objectTypes[random.nextInt(objectTypes.length)]);

            }
        }
        return frameList;
    }

    private static String computeEventId(DeepVISSEvent event, DeepVISSFrame frame) {
        String fromtSalt = "K0Oorvb53ZofZHIrffV48Skpefk4ASSffEWOLbat";
        String backSalt = "cx8nwHumrVfJliMpAf0wYAzztUz2iy55Y2nsS7sV";
        String originalString = fromtSalt + event.getDetection().getBoundingRectangle().getTop() + "-" +
                event.getDetection().getBoundingRectangle().getLeft() + "-" +
                event.getDetection().getBoundingRectangle().getWidth() + "-" +
                event.getDetection().getBoundingRectangle().getHeight() + "-" +
                frame.getTimestamps().get(0).getValue() + "-" + frame.getSourceId() + backSalt;
        String sha512hex = DigestUtils.sha512Hex(originalString);
        return sha512hex;
    }
}

