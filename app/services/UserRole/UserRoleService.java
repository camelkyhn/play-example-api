package services.UserRole;

import middleware.Dtos.UserRoleDto;
import io.ebean.ExpressionList;
import middleware.Bases.IService;
import middleware.Bases.Service;
import middleware.Core.GeneralResult;
import middleware.Entities.Role;
import middleware.Entities.User;
import middleware.Entities.UserRole;
import middleware.Exceptions.DataAlreadyExistException;
import middleware.Exceptions.NotFoundException;
import middleware.Filters.UserRoleFilter;
import services.Role.RoleService;
import services.User.UserService;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class UserRoleService extends Service<Long, UserRole> implements IService<Long, UserRole, UserRoleFilter, UserRoleDto> {

    private UserService _userService;
    private RoleService _roleService;

    @Inject
    public UserRoleService(UserService userService, RoleService roleService) {
        super(UserRole.class);
        _userService = userService;
        _roleService = roleService;
    }

    @Override
    public GeneralResult<UserRole> detail(Long id) {
        GeneralResult<UserRole> result = new GeneralResult<>();
        try {
            UserRole entity = finder.query().where().eq("id", id).findOne();
            if (entity == null) {
                throw new NotFoundException(UserRole.class.getSimpleName());
            }

            result.Success(entity);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    @Override
    public GeneralResult<List<UserRole>> list(UserRoleFilter userFilter) {
        GeneralResult<List<UserRole>> result = new GeneralResult<>();
        try {
            ExpressionList<UserRole> resultList = finder.query().where();
            if (userFilter.getUserName() != null && !userFilter.getUserName().isEmpty()) {
                resultList = resultList.contains("user.name", userFilter.getUserName());
            }

            if (userFilter.getRoleName() != null && !userFilter.getRoleName().isEmpty()) {
                resultList = resultList.contains("role.name", userFilter.getRoleName());
            }

            result.Success(applyBaseFilter(userFilter, resultList));
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    @Override
    public GeneralResult<Boolean> save(UserRoleDto dto) {
        GeneralResult<Boolean> result = new GeneralResult<>();
        try {
            if (dto == null) {
                throw new NotFoundException(UserRole.class.getSimpleName());
            }

            if (!isUniqueToAdd(dto)) {
                throw new DataAlreadyExistException(UserRole.class.getSimpleName());
            }

            GeneralResult<User> userResult = _userService.detail(dto.getUserId());
            if (userResult.isFailed()) {
                throw new NotFoundException(User.class.getSimpleName());
            }

            GeneralResult<Role> roleResult = _roleService.detail(dto.getRoleId());
            if (roleResult.isFailed()) {
                throw new NotFoundException(User.class.getSimpleName());
            }

            GeneralResult<User> updatedUserResult = _userService.detail(dto.getUpdatedUserId());
            if (updatedUserResult.isFailed()) {
                throw new NotFoundException(User.class.getSimpleName());
            }

            UserRole entity = new UserRole(dto.getId(), userResult.getData(), roleResult.getData(), dto.getStatus(), updatedUserResult.getData(), new Date(), new Date());
            finder.db().insert(entity);
            result.Success(true);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    @Override
    public GeneralResult<Boolean> update(UserRoleDto dto) {
        GeneralResult<Boolean> result = new GeneralResult<>();
        try {
            if (!isUniqueToUpdate(dto)) {
                throw new DataAlreadyExistException(UserRole.class.getSimpleName());
            }

            UserRole oldUserRole = finder.byId(dto.getId());
            if (oldUserRole == null) {
                throw new NotFoundException(UserRole.class.getSimpleName());
            }

            GeneralResult<User> userResult = _userService.detail(dto.getUserId());
            if (userResult.isFailed()) {
                throw new NotFoundException(User.class.getSimpleName());
            }

            GeneralResult<Role> roleResult = _roleService.detail(dto.getRoleId());
            if (roleResult.isFailed()) {
                throw new NotFoundException(User.class.getSimpleName());
            }

            GeneralResult<User> updatedUserResult = _userService.detail(dto.getUpdatedUserId());
            if (updatedUserResult.isFailed()) {
                throw new NotFoundException(User.class.getSimpleName());
            }

            oldUserRole.setUser(userResult.getData());
            oldUserRole.setRole(roleResult.getData());
            oldUserRole.setStatus(dto.getStatus());
            oldUserRole.setUpdatedUser(userResult.getData());
            oldUserRole.setUpdatedDate(new Date());
            finder.db().update(oldUserRole);
            result.Success(true);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    @Override
    public GeneralResult<Boolean> delete(Long id) {
        GeneralResult<Boolean> result = new GeneralResult<>();
        try {
            finder.deleteById(id);
            result.Success(true);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    public Boolean isUniqueToAdd(UserRoleDto dto) {
        return finder.query().where()
                .eq("user.id", dto.getUserId())
                .and()
                .eq("role.id", dto.getRoleId())
                .not().exists();
    }

    public Boolean isUniqueToUpdate(UserRoleDto dto) {
        return finder.query().where()
                .not().eq("id", dto.getId())
                .and()
                .eq("user.id", dto.getRoleId())
                .and()
                .eq("role.id", dto.getRoleId())
                .not().exists();
    }
}