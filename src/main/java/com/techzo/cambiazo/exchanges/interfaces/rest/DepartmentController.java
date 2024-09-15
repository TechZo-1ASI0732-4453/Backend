package com.techzo.cambiazo.exchanges.interfaces.rest;


import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllDepartmentsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetDepartmentByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IDepartmentCommandService;
import com.techzo.cambiazo.exchanges.domain.services.IDepartmentQueryService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateDepartmentResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.DepartmentResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateDepartmentCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.DepartmentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api/v2/departments")
@Tag(name="Departments", description="Departments Management Endpoints")
public class DepartmentController {

    private final IDepartmentCommandService departmentCommandService;

    private final IDepartmentQueryService departmentQueryService;

    public DepartmentController(IDepartmentCommandService departmentCommandService, IDepartmentQueryService departmentQueryService) {
        this.departmentCommandService = departmentCommandService;
        this.departmentQueryService = departmentQueryService;
    }

    @Operation(summary="Create a new Department", description="Create a new Department with the input data.")
    @PostMapping
    public ResponseEntity<DepartmentResource>createDepartment(@RequestBody CreateDepartmentResource resource){
        try{
            var createDepartmentCommand = CreateDepartmentCommandFromResourceAssembler.toCommandFromResource(resource);
            var department = departmentCommandService.handle(createDepartmentCommand);
            var departmentResource = DepartmentResourceFromEntityAssembler.toResourceFromEntity(department.get());
            return ResponseEntity.status(CREATED).body(departmentResource);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResource>getDepartmentById(@PathVariable Long id){
        try{
            var getDepartmentQuery=new GetDepartmentByIdQuery(id);
            var department=departmentQueryService.handle(getDepartmentQuery);
            var departmentResource=DepartmentResourceFromEntityAssembler.toResourceFromEntity(department.get());
            return ResponseEntity.ok(departmentResource);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResource>>getAllDepartments(){
        try{
            var getAllDepartmentsQuery = new GetAllDepartmentsQuery();
            var departments = departmentQueryService.handle(getAllDepartmentsQuery);
            var departmentResources = departments.stream()
                    .map(DepartmentResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(departmentResources);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
