package services.Role;

import middleware.Dtos.RoleDto;
import io.ebean.ExpressionList;
import middleware.Bases.IService;
import middleware.Bases.Service;
import middleware.Core.GeneralResult;
import middleware.Entities.Role;
import middleware.Entities.User;
import middleware.Exceptions.NotFoundException;
import middleware.Filters.RoleFilter;
import services.User.UserService;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class RoleService extends Service<Long, Role> implements IService<Long, Role, RoleFilter, RoleDto> {

    private UserService _userService;

    @Inject
    public RoleService(UserService userService) {
        super(Role.class);
        _userService = userService;
    }

    @Override
    public GeneralResult<Role> detail(Long id) {
        GeneralResult<Role> result = new GeneralResult<>();
        try {
            Role entity = finder.query().where().eq("id", id).findOne();
            if (entity == null) {
                throw new NotFoundException(Role.class.getSimpleName());
            }

            result.Success(entity);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    @Override
    public GeneralResult<List<Role>> list(RoleFilter roleFilter) {
        GeneralResult<List<Role>> result = new GeneralResult<>();
        try {
            ExpressionList<Role> resultList = finder.query().where();
            if (roleFilter.getName() != null && !roleFilter.getName().isEmpty()) {
                resultList = resultList.contains("name", roleFilter.getName());
            }

            result.Success(applyBaseFilter(roleFilter, resultList));
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    @Override
    public GeneralResult<Boolean> save(RoleDto dto) {
        GeneralResult<Boolean> result = new GeneralResult<>();
        try {
            if (dto == null) {
                throw new NotFoundException(Role.class.getSimpleName());
            }

            GeneralResult<User> userResult = _userService.detail(dto.getUpdatedUserId());
            if (userResult.isFailed()){
                throw new NotFoundException(User.class.getSimpleName());
            }

            Role entity = new Role(dto.getId(), dto.getName(), dto.getStatus(), userResult.getData(), new Date(), new Date());
            finder.db().insert(entity);
            result.Success(true);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    @Override
    public GeneralResult<Boolean> update(RoleDto dto) {
        GeneralResult<Boolean> result = new GeneralResult<>();
        try {
            Role oldRole = finder.byId(dto.getId());
            if (oldRole == null) {
                throw new NotFoundException(Role.class.getSimpleName());
            }

            GeneralResult<User> userResult = _userService.detail(dto.getUpdatedUserId());
            if (userResult.isFailed()){
                throw new NotFoundException(User.class.getSimpleName());
            }

            oldRole.setName(dto.getName());
            oldRole.setStatus(dto.getStatus());
            oldRole.setUpdatedUser(userResult.getData());
            oldRole.setUpdatedDate(new Date());
            finder.db().update(oldRole);
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
}
