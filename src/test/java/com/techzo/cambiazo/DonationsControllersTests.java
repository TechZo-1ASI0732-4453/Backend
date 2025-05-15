package com.techzo.cambiazo;

import com.techzo.cambiazo.donations.domain.model.aggregates.AccountNumber;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.aggregates.Project;
import com.techzo.cambiazo.donations.domain.model.aggregates.SocialNetwork;
import com.techzo.cambiazo.donations.domain.model.commands.*;
import com.techzo.cambiazo.donations.domain.model.entities.CategoryOng;
import com.techzo.cambiazo.donations.domain.model.queries.*;
import com.techzo.cambiazo.donations.domain.services.*;
import com.techzo.cambiazo.donations.interfaces.rest.*;
import com.techzo.cambiazo.donations.interfaces.rest.resources.*;
import com.techzo.cambiazo.donations.interfaces.rest.transform.SocialNetworkResourceFromEntityAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class DonationsControllersTests {

    @Nested
    class AccountNumberControllerTest {

        @Mock
        private AccountNumberCommandService accountNumberCommandService;

        @Mock
        private AccountNumberQueryService accountNumberQueryService;

        @InjectMocks
        private AccountNumberController accountNumberController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreateAccountNumber() {
            CreateAccountNumberResource resource = new CreateAccountNumberResource("Test Account",
                    "12345678901234567890", "001-123456-0123456789", 1L);
            AccountNumber mockAccountNumber = mock(AccountNumber.class);
            when(mockAccountNumber.getId()).thenReturn(1L);

            when(accountNumberCommandService.handle(any(CreateAccountNumberCommand.class)))
                    .thenReturn(Optional.of(mockAccountNumber));

            ResponseEntity<AccountNumberResource> response = accountNumberController.createAccountNumber(resource);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        void testGetAccountNumberById() {
            Long accountId = 1L;
            AccountNumber mockAccountNumber = mock(AccountNumber.class);
            when(mockAccountNumber.getId()).thenReturn(accountId);

            when(accountNumberQueryService.handle(any(GetAccountNumberByIdQuery.class)))
                    .thenReturn(Optional.of(mockAccountNumber));

            ResponseEntity<AccountNumberResource> response = accountNumberController.getAccountNumberById(accountId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        void testGetAllAccountNumbersByOngId() {
            AccountNumberResource mockAccountNumberResource = mock(AccountNumberResource.class);

            when(accountNumberQueryService.handle(any(GetAccountNumberByIdQuery.class)))
                    .thenReturn(Optional.of(mock(AccountNumber.class)));

            ResponseEntity<List<AccountNumberResource>> response = new ResponseEntity<>(
                    Collections.singletonList(mockAccountNumberResource), HttpStatus.OK);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertFalse(response.getBody().isEmpty());
        }
    }

    @Nested
    class CategoryOngsControllerTest {

        @Mock
        private CategoryOngCommandService categoryOngCommandService;

        @Mock
        private CategoryOngQueryService categoryOngQueryService;

        @InjectMocks
        private CategoryOngsController categoryOngsController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreateCategoryOng() {
            CreateCategoryOngResource resource = new CreateCategoryOngResource("Environment");
            CategoryOng mockCategoryOng = mock(CategoryOng.class);
            when(mockCategoryOng.getId()).thenReturn(1L);

            when(categoryOngCommandService.handle(any(CreateCategoryOngCommand.class)))
                    .thenReturn(Optional.of(mockCategoryOng));

            ResponseEntity<CategoryOngResource> response = categoryOngsController.createCategoryOng(resource);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        void testGetCategoryOngById() {
            Long categoryId = 1L;
            CategoryOng mockCategoryOng = mock(CategoryOng.class);
            when(mockCategoryOng.getId()).thenReturn(categoryId);

            when(categoryOngQueryService.handle(any(GetCategoryOngByIdQuery.class)))
                    .thenReturn(Optional.of(mockCategoryOng));

            ResponseEntity<CategoryOngResource> response = categoryOngsController.getCategoryOngById(categoryId);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        void testGetAllCategoryOngs() {
            CategoryOng mockCategoryOng = mock(CategoryOng.class);
            when(mockCategoryOng.getId()).thenReturn(1L);

            when(categoryOngQueryService.handle(any(GetAllCategoryOngsQuery.class)))
                    .thenReturn(Collections.singletonList(mockCategoryOng));

            ResponseEntity<List<CategoryOngResource>> response = categoryOngsController.getAllCategoryOngs();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertFalse(response.getBody().isEmpty());
        }
    }

    @Nested
    class OngsControllerTest {

        @Mock
        private OngCommandService ongCommandService;

        @Mock
        private OngQueryService ongQueryService;

        @InjectMocks
        private OngsController ongsController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreateOng() {
            CreateOngResource resource = new CreateOngResource("ONG Test", "Type A", "About Us Text",
                    "Mission Vision Text", "Support Method Text", "Address", "email@example.com", "123456789",
                    "logo.png", "website.com", 1L, "Schedule");
            Ong mockOng = mock(Ong.class);
            when(mockOng.getId()).thenReturn(1L);

            when(ongCommandService.handle(any(CreateOngCommand.class)))
                    .thenReturn(Optional.of(mockOng));

            ResponseEntity<OngResource> response = ongsController.createOng(resource);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        void testGetOngById() {
            Long ongId = 1L;
            Ong mockOng = mock(Ong.class);
            when(mockOng.getId()).thenReturn(ongId);

            when(ongQueryService.handle(any(GetOngByIdQuery.class)))
                    .thenReturn(Optional.of(mockOng));

            ResponseEntity<OngResource> response = ongsController.getOngById(ongId);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        void testGetAllOngs() {
            Ong mockOng = mock(Ong.class);
            when(mockOng.getId()).thenReturn(1L);

            when(ongQueryService.handle(any(GetAllOngsQuery.class)))
                    .thenReturn(Collections.singletonList(mockOng));

            ResponseEntity<List<OngResource>> response = ongsController.getAllOngs();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertFalse(response.getBody().isEmpty());
        }

        @Test
        void testUpdateOng() {
            Long ongId = 1L;
            UpdateOngResource resource = new UpdateOngResource("Updated ONG Test", "Type B", "Updated About Us",
                    "Updated Mission", "Updated Support", "New Address", "newemail@example.com", "987654321",
                    "newlogo.png", "newwebsite.com", 1L, "New Schedule");
            Ong mockOng = mock(Ong.class);
            when(mockOng.getId()).thenReturn(ongId);

            when(ongCommandService.handle(any(UpdateOngCommand.class)))
                    .thenReturn(Optional.of(mockOng));

            ResponseEntity<OngResource> response = ongsController.updateOng(ongId, resource);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        void testDeleteOng() {
            Long ongId = 1L;

            ResponseEntity<?> response = ongsController.deleteOng(ongId); 
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); 
        }
    }

    @Nested
    class ProjectControllerTest {

        @Mock
        private ProjectCommandService projectCommandService;

        @Mock
        private ProjectQueryService projectQueryService;

        @InjectMocks
        private ProjectController projectController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreateProject() {
            CreateProjectResource resource = new CreateProjectResource("Project Alpha", "Description", 1L);
            Project mockProject = mock(Project.class);
            when(mockProject.getId()).thenReturn(1L);

            when(projectCommandService.handle(any(CreateProjectCommand.class)))
                    .thenReturn(Optional.of(mockProject));

            ResponseEntity<ProjectResource> response = projectController.createProject(resource);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        void testGetProjectById() {
            Long projectId = 1L;
            Project mockProject = mock(Project.class);
            when(mockProject.getId()).thenReturn(projectId);

            when(projectQueryService.handle(any(GetProjectByIdQuery.class)))
                    .thenReturn(Optional.of(mockProject));

            ResponseEntity<ProjectResource> response = projectController.getProjectById(projectId);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        void testGetAllProjects() {
            Project mockProject = mock(Project.class);
            when(mockProject.getId()).thenReturn(1L);

            when(projectQueryService.handle(any(GetAllProjectsQuery.class)))
                    .thenReturn(Collections.singletonList(mockProject));

            ResponseEntity<List<ProjectResource>> response = projectController.getAllProjects();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertFalse(response.getBody().isEmpty());
        }

        @Test
        void testDeleteProject() {
            Long projectId = 1L;
            ResponseEntity<?> response = projectController.deleteProject(projectId);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        }
    }

    @Nested
    class SocialNetworkControllerTest {

        @Mock
        private SocialNetworkCommandService socialNetworkCommandService;

        @Mock
        private SocialNetworkQueryService socialNetworkQueryService;

        @InjectMocks
        private SocialNetworkController socialNetworkController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreateSocialNetwork() {
            CreateSocialNetworkResource resource = new CreateSocialNetworkResource("Facebook",
                    "http://facebook.com/ong", 1L);
            SocialNetwork mockSocialNetwork = mock(SocialNetwork.class);
            when(mockSocialNetwork.getId()).thenReturn(1L);

            when(socialNetworkCommandService.handle(any(CreateSocialNetworkCommand.class)))
                    .thenReturn(Optional.of(mockSocialNetwork));

            ResponseEntity<SocialNetworkResource> response = socialNetworkController.createSocialNetwork(resource);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        void testGetSocialNetworkById() {
            Long socialNetworkId = 1L;
            SocialNetwork mockSocialNetwork = mock(SocialNetwork.class);
            when(mockSocialNetwork.getId()).thenReturn(socialNetworkId);

            when(socialNetworkQueryService.handle(any(GetSocialNetworkByIdQuery.class)))
                    .thenReturn(Optional.of(mockSocialNetwork));

            ResponseEntity<SocialNetworkResource> response = socialNetworkController
                    .getSocialNetworkById(socialNetworkId);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
        }

        @Test
        void testGetAllSocialNetworksByOngId() {
            Long ongId = 1L;
            SocialNetwork mockSocialNetwork = mock(SocialNetwork.class);
            when(mockSocialNetwork.getId()).thenReturn(1L);

            GetAllSocialNetworkByOngIdQuery query = new GetAllSocialNetworkByOngIdQuery(ongId);
            when(socialNetworkQueryService.handle(query))
                    .thenReturn(Collections.singletonList(mockSocialNetwork));

            SocialNetworkResource mockSocialNetworkResource = mock(SocialNetworkResource.class);
            when(SocialNetworkResourceFromEntityAssembler.toResourceFromEntity(mockSocialNetwork))
                    .thenReturn(mockSocialNetworkResource);

            ResponseEntity<List<SocialNetworkResource>> response = socialNetworkController
                    .getSocialNetworksByOngId(ongId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertFalse(response.getBody().isEmpty());
        }

        @Test
        void testDeleteSocialNetwork() {
            Long socialNetworkId = 1L;
            ResponseEntity<?> response = socialNetworkController.deleteSocialNetwork(socialNetworkId);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        }
    }
}