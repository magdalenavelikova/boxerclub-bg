import { requestFactory } from "./requester";

const baseUrl = "http://localhost:3030/data/comments";

export const commentServiceFactory = (token) => {
  const request = requestFactory();

  const create = async (gameId, data) => {
    const result = await request.post(baseUrl, { gameId, data });

    return result;
  };

  const getAll = async (gameId) => {
    const searchQueryById = encodeURIComponent(`gameId="${gameId}"`);
    const relationQuery = encodeURIComponent(`author=_ownerId`);
    const result = await request.get(`${baseUrl}?where=${searchQueryById}&load=${relationQuery}:users`);
    const comments = result;
    return comments;
  };

  return {
    create,
    getAll,
  };
};
