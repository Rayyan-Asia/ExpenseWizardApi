# Use the official MariaDB image as the base image
FROM mariadb:10.3

# Set environment variables
ENV MYSQL_PASSWORD=pass123 \
    MYSQL_ALLOW_EMPTY_PASSWORD=true \
    MYSQL_DATABASE=expensewizard \
    MYSQL_USER=admin \
    LANG=C.UTF-8 \
    MARIADB_VERSION=1:10.3.39+maria~ubu2004

# Expose the MariaDB port
EXPOSE 3306

# Set metadata for the image
LABEL org.opencontainers.image.authors="MariaDB Community" \
      org.opencontainers.image.description="MariaDB Database for relational SQL" \
      org.opencontainers.image.documentation="https://hub.docker.com/_/mariadb/" \
      org.opencontainers.image.licenses="GPL-2.0" \
      org.opencontainers.image.source="https://github.com/MariaDB/mariadb-docker" \
      org.opencontainers.image.title="MariaDB Database" \
      org.opencontainers.image.url="https://github.com/MariaDB/mariadb-docker" \
      org.opencontainers.image.vendor="MariaDB Community" \
      org.opencontainers.image.version="10.3.39"

# Run MariaDB when the container starts
CMD ["mysqld"]
