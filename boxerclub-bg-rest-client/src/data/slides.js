const breakpoints = [1080, 640, 384, 256, 128];

const unsplashLink = (id, width, height) =>
  require(`../assets/dogs/gallery/${id}.jpg`);

const unsplashPhotos = [
  {
    id: 1,
    width: 800,
    height: 512,
  },
  {
    id: 2,
    width: 800,
    height: 600,
  },
  {
    id: 3,
    width: 800,
    height: 449,
  },
  {
    id: 4,
    width: 800,
    height: 600,
  },
  {
    id: 5,
    width: 800,
    height: 600,
  },
  {
    id: 6,
    width: 800,
    height: 600,
  },
  {
    id: 7,
    width: 800,
    height: 600,
  },
  {
    id: 8,
    width: 800,
    height: 600,
  },
  {
    id: 9,
    width: 800,
    height: 600,
  },
  {
    id: 10,
    width: 800,
    height: 600,
  },
  {
    id: 11,
    width: 800,
    height: 600,
  },
  {
    id: 12,
    width: 551,
    height: 880,
  },
  {
    id: 13,
    width: 640,
    height: 480,
  },
  {
    id: 14,
    width: 640,
    height: 531,
  },
  {
    id: 15,
    width: 640,
    height: 480,
  },
  {
    id: 16,
    width: 640,
    height: 556,
  },
  {
    id: 17,
    width: 640,
    height: 621,
  },
];

export const slides = unsplashPhotos.map((photo) => {
  const width = photo.width * 4;
  const height = photo.height * 4;
  return {
    src: unsplashLink(photo.id),
    width,
    height,
    srcSet: breakpoints.map((breakpoint) => {
      const breakpointHeight = Math.round((height / width) * breakpoint);
      return {
        src: unsplashLink(photo.id),
        width: breakpoint,
        height: breakpointHeight,
      };
    }),
  };
});

export default slides;
