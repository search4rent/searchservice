//package com.randl.search.service.drive
//
///**
// * Created with IntelliJ IDEA.
// * User: cgonzalez
// * Date: 7/29/13
// * Time: 9:15 PM
// * To change this template use File | Settings | File Templates.
// */
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
//import com.google.api.client.http.{FileContent, HttpTransport}
//;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.DriveScopes;
//import com.google.api.services.drive.model.File;
//
//
//
//
//import java.util.Arrays
//import java.lang.String
//import com.google.api.client.json.jackson2.JacksonFactory
//;
//
//
//object DriveCommandLine extends App {
//
//  val CLIENT_ID = "245475219010.apps.googleusercontent.com";
//  val CLIENT_SECRET = "Sv6OuFzGoyEg_2wSEdjen_qV";
//
//  val REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
//
//    val httpTransport: HttpTransport = new NetHttpTransport();
//    val jsonFactory: JsonFactory = new JacksonFactory();
//
//    val flow: GoogleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
//      httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
//      .setAccessType("online")
//      .setApprovalPrompt("auto").build();
//
//    val url: String = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
//    println("Please open the following URL in your browser then type the authorization code:");
//    println("  " + url);
//    val code = readLine();
//
//    val response: GoogleTokenResponse = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
//    val credential: GoogleCredential = new GoogleCredential().setFromTokenResponse(response);
//
//    //Create a new authorized API client
//    val service: Drive = new Drive.Builder(httpTransport, jsonFactory, credential).build();
//
//    //Insert a file
//    val body: File = new File();
//    body.setTitle("My document");
//    body.setDescription("A test document");
//    body.setMimeType("text/plain");
//
//    val fileContent: java.io.File = new java.io.File("dsfdocument.txt");
//    val mediaContent =  new FileContent("text/plain", fileContent);
//
//    val file: File = service.files().insert(body, mediaContent).execute();
//    println("File ID: " + file.getId());
//}