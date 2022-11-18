FROM node:14.17.3-alpine as Builder
RUN mkdir -p /app
WORKDIR /app
COPY . /app
RUN npm install -g cnpm -registry=https://registry.npm.taobao.org

RUN cnpm install -g vue && \
    cnpm install && \
    cnpm run build

FROM nginx:1.20.0
RUN mkdir -p /app
WORKDIR /app
COPY --from=Builder /app/dist /app
RUN chmod -R 755 /app
COPY --from=Builder /app/nginx.conf /etc/nginx/conf.d/
ENV RUN_USER nginx
ENV RUN_GROUP nginx
EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]
