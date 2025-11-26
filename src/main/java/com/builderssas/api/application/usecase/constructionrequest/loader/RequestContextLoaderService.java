package com.builderssas.api.application.usecase.constructionrequest.loader;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.model.user.UserRecord;

import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;

import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.UnauthorizedException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Loads the full domain context required for processing a construction request.
 *
 * Responsibilities:
 *  • Load Project, ConstructionType and User.
 *  • Validate that User has a role allowed to create construction requests.
 *
 * Characteristics:
 *  • Pure application-layer service.
 *  • Immutable records.
 *  • No stock, coordinates, orders or notifications here.
 *  • Fully reactive (Mono).
 */
@Service
@RequiredArgsConstructor
public class RequestContextLoaderService {

    // =====================================================================
    //                           DEPENDENCIES
    // =====================================================================

    private final ProjectRepositoryPort projectRepo;
    private final ConstructionTypeRepositoryPort typeRepo;
    private final UserRepositoryPort userRepo;
    private final UserRoleRepositoryPort userRoleRepo;
    private final RoleRepositoryPort roleRepo;

    // =====================================================================
    //                           INTERNAL RECORD
    // =====================================================================

    /**
     * Unified domain context for the request.
     */
    public record LoadedContext(
            ProjectRecord project,
            ConstructionTypeRecord type,
            UserRecord user,
            RoleRecord role
    ) {}

    // =====================================================================
    //                           PUBLIC API
    // =====================================================================

    /**
     * Loads the Project, ConstructionType, User and Role associated to a request.
     *
     * @param cmd ConstructionRequestRecord input command
     * @return LoadedContext containing the domain objects
     */
    public Mono<LoadedContext> load(ConstructionRequestRecord cmd) {

        Mono<ProjectRecord> mProject =
                projectRepo.findById(cmd.projectId())
                        .switchIfEmpty(Mono.error(
                                new ResourceNotFoundException("El proyecto no existe.")
                        ));

        Mono<ConstructionTypeRecord> mType =
                typeRepo.findById(cmd.constructionTypeId())
                        .switchIfEmpty(Mono.error(
                                new ResourceNotFoundException("El tipo de construcción no existe.")
                        ));

        Mono<UserRecord> mUser =
                userRepo.findById(cmd.requestedByUserId())
                        .switchIfEmpty(Mono.error(
                                new ResourceNotFoundException("El usuario no existe.")
                        ));

        Mono<RoleRecord> mRole =
                userRoleRepo.findByUserId(cmd.requestedByUserId())
                        .next()
                        .flatMap(ur -> roleRepo.findById(ur.roleId()))
                        .switchIfEmpty(Mono.error(
                                new UnauthorizedException("El usuario no tiene un rol válido.")
                        ));

        return mProject.flatMap(project ->
                mType.flatMap(type ->
                        mUser.flatMap(user ->
                                mRole.map(role ->
                                        new LoadedContext(project, type, user, role)
                                )
                        )
                )
        );


    }
}
