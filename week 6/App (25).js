import axios from "axios";
import GitClient from "./GitClient";

// Tell Jest to replace the real axios module with an auto-mocked version
// for this test file, so no real network call to api.github.com happens
jest.mock("axios");

describe("Git Client Tests", () => {
  test("should return repository names for techiesyed", async () => {
    // Dummy data that stands in for the real GitHub API response
    const dummyRepos = {
      data: [
        { id: 1, name: "ArrayListDemo" },
        { id: 2, name: "GenericsDemo" },
        { id: 3, name: "CleanArchitecture" }
      ]
    };

    // Mock axios.get to resolve with our dummy data instead of hitting
    // the network
    axios.get.mockResolvedValue(dummyRepos);

    const response = await GitClient.getRepositories("techiesyed");

    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toHaveBeenCalledWith(
      "https://api.github.com/users/techiesyed/repos"
    );
    expect(response.data).toEqual(dummyRepos.data);
    expect(response.data.map((repo) => repo.name)).toEqual([
      "ArrayListDemo",
      "GenericsDemo",
      "CleanArchitecture"
    ]);
  });
});
