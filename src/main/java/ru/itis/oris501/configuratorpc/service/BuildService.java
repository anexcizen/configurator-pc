package ru.itis.oris501.configuratorpc.service;

import ru.itis.oris501.configuratorpc.dao.BuildDao;
import ru.itis.oris501.configuratorpc.dao.impl.BuildDaoJdbc;
import ru.itis.oris501.configuratorpc.model.Build;
import ru.itis.oris501.configuratorpc.model.Component;
import ru.itis.oris501.configuratorpc.model.User;

import java.util.List;
import java.util.Optional;

public class BuildService {
    private final BuildDao buildDao;
    private final ComponentService componentService;
    private final CategoryService categoryService;

    public BuildService() {
        this(new BuildDaoJdbc(), new ComponentService(), new CategoryService());
    }

    public BuildService(ComponentService componentService, CategoryService categoryService) {
        this(new BuildDaoJdbc(), componentService, categoryService);
    }

    public BuildService(BuildDao buildDao, ComponentService componentService, CategoryService categoryService) {
        this.buildDao = buildDao;
        this.componentService = componentService;
        this.categoryService = categoryService;
    }

    public Build createBuild(int userId, String title, String description, boolean isPublic) {
        idCheck(userId);
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }

        return buildDao.save(new Build(userId, title, description, isPublic));
    }

    public List<Build> getUserBuilds(int userId) {
        idCheck(userId);

        return buildDao.findByUserId(userId);
    }

    public Optional<Build> getById(int id) {
        idCheck(id);

        return buildDao.findById(id);
    }

    public void addComponent(int buildId, int componentId, int userId) {
        idCheck(buildId);
        idCheck(componentId);

        checkOwnership(buildId, userId);

        Component newComp = componentService.findById(componentId).orElseThrow(() -> new IllegalArgumentException("Компонент не найден"));
        checkCountLimit(buildId, newComp);
        checkCompatibility(buildId, newComp);

        buildDao.addComponent(buildId, componentId);
    }

    private void checkCountLimit(int buildId, Component newComp) {
        List<Component> existing = getComponents(buildId);

        String newCat = categoryService.findById(newComp.getCategoryId()).get().getName();

        long count = existing.stream()
                .filter(c -> {
                    String cat = categoryService.findById(c.getCategoryId()).get().getName();
                    return cat.equals(newCat);
                })
                .count();

        if (newCat.equals("Процессор")) {
            if (count >= 1) throw new IllegalArgumentException("В сборке может быть только один процессор");
        } else if (newCat.equals("Материнская плата")) {
            if (count >= 1) throw new IllegalArgumentException("В сборке может быть только одна материнская плата");
        } else if (newCat.equals("Видеокарта")) {
            if (count >= 1) throw new IllegalArgumentException("В сборке не может быть больше одной видеокарты");
        } else if (newCat.equals("Блок питания")) {
            if (count >= 1) throw new IllegalArgumentException("В сборке может быть только один блок питания");
        } else if (newCat.equals("Корпус")) {
            if (count >= 1) throw new IllegalArgumentException("В сборке может быть только один корпус");
        } else if (newCat.equals("Оперативная память")) {
            if (count >= 4) {
                throw new IllegalArgumentException("В сборке может быть не больше 4 планок оперативной памяти");
            }
        }
    }

    public void checkCompatibility(int buildId, Component newComp) {
        List<Component> existing = getComponents(buildId);

        Component mathPlate = null;
        Component cpu = null;
        Component ram = null;
        Component powerSupply = null;
        int currentPower = 0;

        for (Component c : existing) {
            String catName = categoryService.findById(c.getCategoryId()).get().getName();
            if ("Материнская плата".equals(catName)) mathPlate = c;
            if ("Процессор".equals(catName)) cpu = c;
            if ("Оперативная память".equals(catName)) ram = c;
            if ("Блок питания".equals(catName)) {
                powerSupply = c;
                continue;
            }
            if (c.getPowerWatts() != null) currentPower += c.getPowerWatts();
        }

        String newCatName = categoryService.findById(newComp.getCategoryId()).get().getName();



        if (("Процессор".equals(newCatName)) && mathPlate != null) {
            if (newComp.getSocket() != null && mathPlate.getSocket() != null && !newComp.getSocket().equals(mathPlate.getSocket())) {
                throw new IllegalArgumentException("Несовместимый сокет: у CPU " + newComp.getSocket() + ", а у материнской платы " + mathPlate.getSocket());
            }
        }

        if (("Материнская плата".equals(newCatName)) && cpu != null) {
            if (newComp.getSocket() != null && cpu.getSocket() != null && !newComp.getSocket().equals(cpu.getSocket())) {
                throw new IllegalArgumentException("Несовместимый сокет: у CPU " + cpu.getSocket() + ", а у материнской платы " + newComp.getSocket());
            }
        }

        if (("Оперативная память".equals(newCatName)) && mathPlate != null) {
            if (newComp.getRamType() != null && mathPlate.getRamType() != null && !newComp.getRamType().equals(mathPlate.getRamType())) {
                throw new IllegalArgumentException("Несовместимый тип памяти: у RAM " + newComp.getRamType() + ", а у материнки " + mathPlate.getRamType());
            }
        }

        if (("Материнская плата".equals(newCatName)) && ram != null) {
            if (newComp.getRamType() != null && ram.getRamType() != null && !newComp.getRamType().equals(ram.getRamType())) {
                throw new IllegalArgumentException("Несовместимый тип памяти: у RAM " + ram.getRamType() + ", а у материнки " + newComp.getRamType());
            }
        }

        if (powerSupply != null && newComp.getPowerWatts() != null) {
            int totalPower = currentPower + newComp.getPowerWatts();
            if (totalPower > powerSupply.getPowerWatts()) {
                throw new IllegalArgumentException("Не хватает мощности БП! Нужно " + totalPower + " Вт, а БП на " + powerSupply.getPowerWatts() + " Вт.");
            }
        }

    }

    public void delete(int id, int userId) {
        idCheck(id);

        Optional<Build> build = buildDao.findById(id);
        if (build.isEmpty()) {
            throw new IllegalArgumentException("Сборка не найдена");
        }
        if (build.get().getUserId() != userId) {
            throw new IllegalArgumentException("Вы можете удалять только свои сборки");
        }

        buildDao.delete(id);
    }

    public void removeComponent(int buildId, int componentId, int userId) {
        idCheck(buildId);
        idCheck(componentId);

        checkOwnership(buildId, userId);

        buildDao.removeComponent(buildId, componentId);
    }

    public void updateBuild(int buildId, int userId, String title, String description, boolean isPublic) {
        idCheck(buildId);
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Название не может быть пустым");

        Build build = buildDao.findById(buildId)
                .orElseThrow(() -> new IllegalArgumentException("Сборка не найдена"));

        if (build.getUserId() != userId) {
            throw new IllegalArgumentException("Вы можете редактировать только свои сборки");
        }

        build.setTitle(title);
        build.setDescription(description);
        build.setPub(isPublic);
        buildDao.update(build);
    }

    public List<Component> getComponents(int buildId) {
        idCheck(buildId);
        return buildDao.findComponentsByBuildId(buildId);
    }

    public List<Build> getPublicBuilds() {
        return buildDao.findPublicBuilds();
    }

    public Build getBuildForUser(int buildId, User user) {
        Build build = getById(buildId).orElseThrow(() -> new IllegalArgumentException("Сборка не найдена"));

        if (!build.isPub() && build.getUserId() != user.getId()) {
            throw new SecurityException("Доступ запрещён");
        }
        return build;
    }

    private void checkOwnership(int buildId, int userId) {
        Build build = buildDao.findById(buildId)
                .orElseThrow(() -> new IllegalArgumentException("Сборка не найдена"));
        if (build.getUserId() != userId) {
            throw new SecurityException("Вы можете изменять только свои сборки");
        }
    }

    private static void idCheck(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id должен быть > 0");
        }
    }
}
