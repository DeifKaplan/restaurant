package ch.kplan.domain;

final class DomainObjectHelper {

    public static <T> T createInstance(Class<T> clazz) {
        try {
            var constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create instance of " + clazz.getName(), e);
        }
    }
}
