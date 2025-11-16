package planner.demo.controllers;

import planner.demo.DTO.common.ApiResponse;
import planner.demo.DTO.user.UserDTO;
import planner.demo.models.User;
import planner.demo.security.CurrentUser;
import planner.demo.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed-origins}")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser(@CurrentUser User currentUser) {
        UserDTO dto = DtoMapper.toUserDTO(currentUser);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(
            @PathVariable Long userId,
            @CurrentUser User currentUser) {

        // In a real app, you might want to add privacy controls here
        // For now, we'll just return basic user info
        UserDTO dto = DtoMapper.toUserDTO(currentUser);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
//'``
//
//        ---
//
//        ## 🎯 API Endpoints Summary
//
//### **Authentication:**
//        ```
//POST   /api/auth/register         - Register new user
//POST   /api/auth/login            - Login
//GET    /api/auth/validate         - Validate JWT token
//```
//
//        ### **Trips:**
//        ```
//POST   /api/trips                 - Create trip
//GET    /api/trips                 - Get all trips for user
//GET    /api/trips/upcoming        - Get upcoming trips
//GET    /api/trips/{id}            - Get trip by ID
//PUT    /api/trips/{id}            - Update trip
//DELETE /api/trips/{id}            - Delete trip
//POST   /api/trips/{id}/collaborators        - Add collaborator
//DELETE /api/trips/{id}/collaborators/{uid}  - Remove collaborator
//GET    /api/trips/search?destination=...    - Search trips
//```
//
//        ### **Activities:**
//        ```
//POST   /api/trips/{id}/activities              - Create activity
//GET    /api/trips/{id}/activities              - Get all activities
//GET    /api/trips/{id}/activities/day/{day}    - Get activities by day
//PUT    /api/trips/{id}/activities/{aid}        - Update activity
//DELETE /api/trips/{id}/activities/{aid}        - Delete activity
//```
//
//        ### **Expenses:**
//        ```
//POST   /api/trips/{id}/expenses           - Create expense
//GET    /api/trips/{id}/expenses           - Get all expenses
//GET    /api/trips/{id}/expenses/summary   - Get expense summary
//PUT    /api/trips/{id}/expenses/{eid}     - Update expense
//DELETE /api/trips/{id}/expenses/{eid}     - Delete expense
//```
//
//        ### **Users:**
//        ```
//GET    /api/users/me              - Get current user
//GET    /api/users/{id}            - Get user by ID
