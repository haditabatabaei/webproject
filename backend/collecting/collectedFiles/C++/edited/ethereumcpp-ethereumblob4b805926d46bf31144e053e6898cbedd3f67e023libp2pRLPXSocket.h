


#pragma once

#include "Common.h"

namespace dev
{
namespace p2p
{


class RLPXSocket: public std::enable_shared_from_this<RLPXSocket>
{
public:
	RLPXSocket(ba::io_service& _ioService): m_socket(_ioService) {}
	~RLPXSocket() { close(); }
	
	bool isConnected() const { return m_socket.is_open(); }
	void close() { try { boost::system::error_code ec; m_socket.shutdown(bi::tcp::socket::shutdown_both, ec); if (m_socket.is_open()) m_socket.close(); } catch (...){} }
	bi::tcp::endpoint remoteEndpoint() { boost::system::error_code ec; return m_socket.remote_endpoint(ec); }
	bi::tcp::socket& ref() { return m_socket; }
	
protected:
	bi::tcp::socket m_socket;
};

}
}