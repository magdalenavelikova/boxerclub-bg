FROM node:18-alpine
RUN mkdir /static
ADD . /static
WORKDIR "/static"

EXPOSE  3000

CMD ["sh", "-c", "npm run start"] 