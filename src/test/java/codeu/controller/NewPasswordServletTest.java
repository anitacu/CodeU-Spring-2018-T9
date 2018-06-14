package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public class NewPasswordServletTest {
    private NewPasswordServlet newPasswordServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private RequestDispatcher mockRequestDispatcher;

    @Before
    public void setup() throws IOException {
        newPasswordServlet = new NewPasswordServlet();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/newPassword.jsp"))
                .thenReturn(mockRequestDispatcher);
    }
    @Test
    public void testDoGet() throws IOException, ServletException {
        newPasswordServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
    /**test for when a user enters a bad password*/
    @Test
    public void testDoPost_BadShortPassword() throws IOException, ServletException {
        Mockito.when(mockRequest.getParameter("usernameVerification")).thenReturn("test username");
        Mockito.when(mockRequest.getParameter("newPassword")).thenReturn("bad1");
        Mockito.when(mockRequest.getParameter("reTypedNewPassword")).thenReturn("bad1");
        UserStore mockUserStore = Mockito.mock(UserStore.class);
        Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(true);
        newPasswordServlet.setUserStore(mockUserStore);

        newPasswordServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockRequest)
                .setAttribute("error", "Password must be between 5 and 13 characters and contain both letters and numbers.");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost_BadShortLongPassword() throws IOException, ServletException {
        Mockito.when(mockRequest.getParameter("usernameVerification")).thenReturn("test username");
        Mockito.when(mockRequest.getParameter("newPassword")).thenReturn("badPassword12345");
        Mockito.when(mockRequest.getParameter("reTypedNewPassword")).thenReturn("badPassword12345");
        UserStore mockUserStore = Mockito.mock(UserStore.class);
        Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(true);
        newPasswordServlet.setUserStore(mockUserStore);

        newPasswordServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockRequest)
                .setAttribute("error", "Password must be between 5 and 13 characters and contain both letters and numbers.");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    /**test for when a user does not exist.*/
    @Test
    public void testDoPost_NotAnExistingUser() throws IOException, ServletException {
        Mockito.when(mockRequest.getParameter("usernameVerification")).thenReturn("test username");
        Mockito.when(mockRequest.getParameter("newPassword")).thenReturn("testpassword1");
        Mockito.when(mockRequest.getParameter("reTypedNewPassword")).thenReturn("testpassword1");

        UserStore mockUserStore = Mockito.mock(UserStore.class);
        Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(false);
        newPasswordServlet.setUserStore(mockUserStore);

        newPasswordServlet.doPost(mockRequest, mockResponse);
        Mockito.verify(mockRequest).setAttribute("error", "That username was not found.");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost_ExistingUser() throws IOException, ServletException {
        User user =
                new User(
                        UUID.randomUUID(),
                        "test username",
                        "$2a$10$.e.4EEfngEXmxAO085XnYOmDntkqod0C384jOR9oagwxMnPNHaGLa",
                        Instant.now(),
                        false);

       // Mockito.verify(mockUserStore).addUser(user);

        UserStore mockUserStore = Mockito.mock(UserStore.class);
        Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(true);
        Mockito.when(mockUserStore.getUser("test username")).thenReturn(user);
        //Mockito.verify(mockUserStore).addUser(user);
        newPasswordServlet.setUserStore(mockUserStore);

        Mockito.when(mockRequest.getParameter("usernameVerification")).thenReturn("test username");
        Mockito.when(mockRequest.getParameter("newPassword")).thenReturn("testpassword1");
        Mockito.when(mockRequest.getParameter("reTypedNewPassword")).thenReturn("testpassword1");




        newPasswordServlet.setUserStore(mockUserStore);


        String hashedPassword = BCrypt.hashpw("testpassword1", BCrypt.gensalt());


        newPasswordServlet.doPost(mockRequest, mockResponse);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        
        Assert.assertEquals("test username", user.getName());
       Assert.assertNotEquals(
               "$2a$10$.e.4EEfngEXmxAO085XnYOmDntkqod0C384jOR9oagwxMnPNHaGLa", user.getPasswordHash());



        Mockito.verify(mockResponse).sendRedirect("/login");
    }






}