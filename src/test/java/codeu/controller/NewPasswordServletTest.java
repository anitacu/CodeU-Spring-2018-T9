package codeu.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
}
